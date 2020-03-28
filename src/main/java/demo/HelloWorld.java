package demo;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.servlet.UrlMatch;

@UrlMatch(urlMatch = "/helloworld")
public class HelloWorld implements Servlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setStatus(HTTPStatus.OK);
        response.setBody("Hello World");
        response.writeToBrowser();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
    }
}
