package com.lkm.webserver.request;

import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;

public class RequestParser {
    public static String[] parseMessage(String message) {
        String[] result = new String[3];
        // split header and body
        String[] tmp = message.split(Misc.CRLF + Misc.CRLF, 2);
        // post body
        result[2] = tmp[1];
        String[] lineAndHeader = tmp[0].split(Misc.CRLF, 2);
        // request line
        result[0] = lineAndHeader[0];
        // headers
        result[1] = lineAndHeader[1];
        return result;
    }

    public static RequestLine parseRequestLine(String message) throws Exception {
        HashMap<String, String> query = null;
        RequestMethod method;
        String path;

        // parse request method
        String[] messageArr = message.split(" ");
        if ("GET".equals(messageArr[0].toUpperCase())) {
            method = RequestMethod.GET;
        } else if ("POST".equals(messageArr[0].toUpperCase())) {
            method = RequestMethod.POST;
        } else {
            throw new Exception("Unsupported request method");
        }

        // parse path and query
        // such as /asd/qwe/zxc.html?tyu=x&opq=jkl
        String url = messageArr[1];
        String[] pathArr = url.split("\\?");
        String queryString = "";

        if (pathArr.length > 1) {
            queryString = pathArr[1];
            query = parseQueryString(pathArr[1]);
        }
        return new RequestLine(query == null ? new HashMap<>() : query, method, pathArr[0], queryString);
    }

    public static RequestHeaders parseRequestHeaders(String message) throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> cookies = new HashMap<>();
        String[] headerArr = message.split(Misc.CRLF);
        for (String header : headerArr) {
            String[] arg = header.split(":", 2);
            headers.put(arg[0].trim(), arg[1].trim());

            // cookie: a=1; b=2; c=3
            if ("cookie".equals(arg[0].trim().toLowerCase())) {
                String[] cookieArr = arg[1].split(";");
                for (String cookie : cookieArr) {
                    // a=1
                    String[] kv = cookie.split("=", 2);
                    //               a             1
                    if (kv.length > 1) {
                        cookies.put(kv[0].trim(), kv[1].trim());
                    }
                }
            }
        }
        return new RequestHeaders(headers, cookies);
    }

    private static HashMap<String, String> parseQueryString(String message) {
        HashMap<String, String> result = new HashMap<>();
        String[] queryArr = message.split("&");
        for (String arg : queryArr) {
            String[] argArr = arg.split("=");
            // qwe=asd -> key=qwe value=asd
            if (argArr.length > 1) {
                result.put(argArr[0], argArr[1]);
            }
        }
        return result;
    }
}
