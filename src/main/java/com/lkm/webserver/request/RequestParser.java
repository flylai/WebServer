package com.lkm.webserver.request;

import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.constant.RequestMethod;
import com.lkm.webserver.util.StringUtil;

import java.util.HashMap;
import java.util.List;

public class RequestParser {
    public static String[] parseMessage(String message) {
        String[] result = new String[3];
        // split header and body
        List<String> tmp = StringUtil.split(message, Misc.CRLF + Misc.CRLF, 3);
        // post body
        if (tmp.size() > 1) {
            result[2] = tmp.get(1);
        }
        List<String> lineAndHeader = StringUtil.split(tmp.get(0), Misc.CRLF, 2);
        // request line
        result[0] = lineAndHeader.get(0);
        // headers
        result[1] = lineAndHeader.get(1);
        return result;
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
            headers.put(arg.get(0).trim(), arg.get(1).trim());

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
}
