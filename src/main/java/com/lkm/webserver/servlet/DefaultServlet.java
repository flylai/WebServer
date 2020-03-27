package com.lkm.webserver.servlet;

import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.request.Request;
import com.lkm.webserver.response.Response;

import java.io.File;
import java.io.FileInputStream;

public class DefaultServlet implements Servlet {
    @Override
    public void doGet(Request request, Response response) {
        String path = request.getRequestPath();
        File file = new File(Misc.WWW_ROOT + path);
        byte[] fileContent;
        if (file.isFile() && file.exists()) {
            response.setStatus(HTTPStatus.OK);
            long size = file.length();
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileContent = new byte[(int) size];
                fileInputStream.read(fileContent);
                response.setBody(fileContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(HTTPStatus.Not_Found);
            response.setBody("404 NOT FOUND!!!");
        }
        response.writeToBrowser();
    }

    @Override
    public void doPost(Request request, Response response) {
    }
}
