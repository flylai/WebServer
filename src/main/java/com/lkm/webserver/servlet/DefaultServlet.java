package com.lkm.webserver.servlet;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.constant.Misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultServlet implements Servlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        String path = request.getRequestPath();
        path = Misc.WWW_ROOT + path;
        byte[] fileContent = new byte[0];
        String range = request.getHeader("range");
        File file = new File(path);

        if (file.isFile() && file.exists()) {
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mimeType == null) {
                String filename = file.getName();
                int position = filename.lastIndexOf('.');
                String extension = filename.substring(position + 1);
                switch (extension) {
                    case "js":
                        mimeType = "text/javascript";
                        break;
                    case "css":
                        mimeType = "text/css";
                        break;
                    case "json ":
                        mimeType = "application/json";
                        break;
                    default:
                        mimeType = null;
                        break;
                }
            }
            if (mimeType != null) {
                response.setHeader("content-type", mimeType);
            }

            try {
                if (!range.isEmpty()) {
                    response.setStatus(HTTPStatus.Partial_Content);
                    String[] rangeArr = range.split("=")[1].trim().split("-");
                    int start = Integer.parseInt(rangeArr[0]);
                    int end = Integer.parseInt(rangeArr[1]);
                    fileContent = readFile(file, start, end);
                    response.setHeader("content-range", "bytes " + start + "-" + end + "/" + fileContent.length);
                } else {
                    response.setStatus(HTTPStatus.OK);
                    fileContent = readFile(file, -1, -1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setBody(fileContent);
        } else {
            response.setStatus(HTTPStatus.Not_Found);
            response.setBody("404 NOT FOUND!!!");
        }
        response.writeToBrowser();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
    }

    private byte[] readFile(File file, int start, int end) throws IOException {
        byte[] fileContent = new byte[0];
        FileInputStream fileInputStream;
        if (file.exists() && file.isFile()) {
            fileInputStream = new FileInputStream(file);
            if (start != -1 && end != -1) {
                fileInputStream.skip(start);
                fileContent = new byte[end - start];
            } else {
                fileContent = new byte[(int) file.length()];
            }
            fileInputStream.read(fileContent);
            fileInputStream.close();
        }
        return fileContent;
    }
}
