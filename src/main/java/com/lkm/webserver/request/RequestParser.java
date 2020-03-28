package com.lkm.webserver.request;

import com.lkm.webserver.connection.Connection;
import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.constant.RequestMethod;
import com.lkm.webserver.exception.ContentLengthError;
import com.lkm.webserver.util.ByteArrayUtil;
import com.lkm.webserver.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestParser {
    public static Request parseMessage(Connection connection) throws Exception {
        ByteArrayOutputStream messageStream = connection.getHttpMessage();
        byte[] message = messageStream.toByteArray();
        int bodyIndex = ByteArrayUtil.indexof(message, Misc.CRLF + Misc.CRLF);
        int lineIndex = ByteArrayUtil.indexof(message, Misc.CRLF);

        byte[] requestLineByteArr = new byte[lineIndex];
        System.arraycopy(message, 0, requestLineByteArr, 0, lineIndex);

        // 2 means CRLF -> "\r\n"
        byte[] requestHeaderByteArr = new byte[bodyIndex - lineIndex - 2];
        System.arraycopy(message, lineIndex + 2, requestHeaderByteArr, 0, bodyIndex - lineIndex - 2);

        RequestLine requestLine = null;
        RequestHeaders requestHeaders = null;
        RequestBody requestBody = null;

        requestLine = parseRequestLine(new String(requestLineByteArr));
        requestHeaders = parseRequestHeaders(new String(requestHeaderByteArr));
        if (requestLine.getMethod() == RequestMethod.POST) {
            int bodyLen = Integer.parseInt(requestHeaders.header("content-length"));
            long startTime = System.currentTimeMillis();
            boolean interrupt = false;
            // 4 means CRLF x 2 -> "\r\n\r\n"
            while (messageStream.size() - bodyIndex - 4 != bodyLen) {
                // blocking... waiting for body
                if (System.currentTimeMillis() - startTime > Misc.BLOCK_TIMEOUT) {
                    interrupt = true;
                    break;
                }
            }
            if (interrupt) {
                throw new ContentLengthError(bodyLen, messageStream.size());
            }
            byte[] requestBodyByteArr = new byte[bodyLen];
            System.arraycopy(messageStream.toByteArray(), bodyIndex + 4, requestBodyByteArr, 0, bodyLen);
            requestBody = parseBody(requestHeaders, requestBodyByteArr);
        }

        return new Request(requestLine, requestHeaders, requestBody);
    }

    public static RequestLine parseRequestLine(String message) throws Exception {
        HashMap<String, String> query = null;
        RequestMethod method;

        // parse request method
        List<String> messageArr = StringUtil.split(message, " ");
        if ("GET".equals(messageArr.get(0).toUpperCase())) {
            method = RequestMethod.GET;
        } else if ("POST".equals(messageArr.get(0).toUpperCase())) {
            method = RequestMethod.POST;
        } else {
            throw new Exception("Unsupported request method");
        }

        // parse path and query
        // such as /asd/qwe/zxc.html?tyu=x&opq=jkl
        String url = messageArr.get(1);
        List<String> pathArr = StringUtil.split(url, "?");
        String queryString = "";

        String path = pathArr.size() > 0 ? pathArr.get(0) : "/";

        if (pathArr.size() > 1) {
            queryString = pathArr.get(1);
            query = parseQueryString(pathArr.get(1));
        }
        return new RequestLine(query == null ? new HashMap<>() : query, method, path, queryString);
    }

    public static RequestHeaders parseRequestHeaders(String message) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> cookies = new HashMap<>();
        List<String> headerArr = StringUtil.split(message, Misc.CRLF);
        for (String header : headerArr) {
            List<String> arg = StringUtil.split(header, ":", 2);
            headers.put(arg.get(0).trim().toLowerCase(), arg.get(1).trim());

            // cookie: a=1; b=2; c=3
            if ("cookie".equals(arg.get(0).trim().toLowerCase())) {
                List<String> cookieArr = StringUtil.split(arg.get(1), ";");
                for (String cookie : cookieArr) {
                    // a=1
                    List<String> kv = StringUtil.split(cookie, "=", 2);
                    if (kv.size() > 1) {
                        //                 a                 1
                        cookies.put(kv.get(0).trim(), kv.get(1).trim());
                    }
                }
            }
        }
        return new RequestHeaders(headers, cookies);
    }

    private static HashMap<String, String> parseQueryString(String message) {
        HashMap<String, String> result = new HashMap<>();
        List<String> queryArr = StringUtil.split(message, "&");
        for (String arg : queryArr) {
            List<String> argArr = StringUtil.split(arg, "=");
            // qwe=asd -> key=qwe value=asd
            if (argArr.size() > 1) {
                result.put(argArr.get(0), argArr.get(1));
            }
        }
        return result;
    }

    public static RequestBody parseBody(RequestHeaders header, byte[] body) {
        RequestBody requestBody = new RequestBody();
        String contentType = header.header("content-type");
        List<String> contentTypeArr = StringUtil.split(contentType, ";");
        HashMap<String, String> postQueryStringBody;
        if (contentTypeArr.size() < 1) {
            return null;
        }
        String type = contentTypeArr.get(0).trim().toLowerCase();
        if ("application/x-www-form-urlencoded".equals(type)) {
            // for raw post data
            postQueryStringBody = parseQueryString(contentTypeArr.get(0));
            requestBody.setPostData(postQueryStringBody);
        } else if ("multipart/form-data".equals(type)) {
            int pos = contentTypeArr.get(1).indexOf("=");
            String boundary = contentTypeArr.get(1).substring(pos + 1);

            boundary = Misc.CRLF + "--" + boundary;
            int prePos = boundary.length();
            int curPos = ByteArrayUtil.indexof(body, boundary, boundary.length());
            List<byte[]> datas = new ArrayList<>();
            while (curPos != -1) {
                byte[] data = new byte[curPos - prePos];
                System.arraycopy(body, prePos, data, 0, curPos - prePos);
                datas.add(data);
                prePos = curPos + boundary.length();
                curPos = ByteArrayUtil.indexof(body, boundary, prePos);
            }

            for (byte[] data : datas) {
                // split header and body
                int crlfPosition = ByteArrayUtil.indexof(data, Misc.CRLF + Misc.CRLF);
                /*
                    --Boundary
                    content-xxx
                    content-yyy
                    content-zzz

                    real content
                    --Boundary--
                 */
                List<String> subContent = StringUtil.split(new String(data, 0, crlfPosition),
                        Misc.CRLF + Misc.CRLF, 2);
                byte[] realBody = new byte[data.length - crlfPosition - 4];
                System.arraycopy(data, crlfPosition + 4, realBody, 0, data.length - crlfPosition - 4);

                // split content xxx/yyy/zzz
                List<String> subHeader = StringUtil.split(subContent.get(0), Misc.CRLF);

                String name = "";
                String value = "";
                boolean isFile = false;

                for (String h : subHeader) {
                    if (h.isEmpty()) {
                        continue;
                    }
                    // split content-xxx:qwe=111;aaa=bbb;ccc="ddd"
                    List<String> item = StringUtil.split(h, ":");
                    // split qwe=111;aaa=bbb;ccc="ddd"
                    List<String> args = StringUtil.split(item.get(1), ";");
                    for (String arg : args) {
                        // split qwe=111
                        List<String> kv = StringUtil.split(arg, "=");
                        String tmpName = kv.get(0).trim().toLowerCase();
                        if (kv.size() > 1) {
                            if ("name".equals(tmpName)) {
                                name = kv.get(1).substring(1, kv.get(1).length() - 1);
                            }
                            if ("filename".equals(tmpName)) {
                                // ignore it, only content-disposition:xxx;file;yyy mark as file
                                value = kv.get(1).substring(1, kv.get(1).length() - 1);
                            }
                        } else {
                            if ("file".equals(tmpName)) {
                                isFile = true;
                            }
                        }
                    }
                }
                if (isFile) {
                    //         name="aaa" filename="bbb"
                    requestBody.getPostData().put(name, value);
                    //                     filename  file content
                    requestBody.getFile().put(value, realBody);
                } else {
                    requestBody.getPostData().put(name, new String(realBody));
                }
            }

        }
        return requestBody;
    }
}
