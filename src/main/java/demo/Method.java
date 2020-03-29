package demo;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.servlet.UrlMatch;

@UrlMatch(urlMatch = "/method")
public class Method implements Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        request.getQueryMap().forEach((k, v) -> {
            stringBuilder.append(k).append("=").append(v).append("\n");
        });

        response.setBody(stringBuilder.toString());
        response.setStatus(HTTPStatus.OK);
        response.writeToBrowser();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        request.getPostData().forEach((k, v) -> {
            stringBuilder.append(k).append("=").append(v).append("\n");
        });

        response.setBody(stringBuilder.toString());
        response.setStatus(HTTPStatus.OK);
        response.writeToBrowser();
    }
}
