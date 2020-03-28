package demo;


import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.servlet.UrlMatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@UrlMatch(urlMatch = "/fileupload")
public class FileUpload implements Servlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (!request.getQueryValue("up").isEmpty()) {

        } else {
            File file = new File(Misc.WWW_ROOT + "/fileupload.html");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileContent = new byte[(int) file.length()];
                fileInputStream.read(fileContent);

                response.setStatus(HTTPStatus.OK);
                response.setBody(fileContent);
                response.writeToBrowser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String path = Misc.WWW_ROOT + "/savefile";
        if (request.getFile() != null && request.getFile().size() != 0) {
            for (Map.Entry<String, byte[]> entry : request.getFile().entrySet()) {
                File file = new File(path);
                try {
                    byte[] fileContent = entry.getValue();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(fileContent);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.setStatus(HTTPStatus.OK);
        response.setBody("data->" + request.getPostData("data") + "\nfile saved at " + path);
        response.writeToBrowser();
    }
}
