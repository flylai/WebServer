package demo;

import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.request.Request;
import com.lkm.webserver.response.Response;
import com.lkm.webserver.servlet.UrlMatch;

@UrlMatch(urlMatch = "/helloworld")
public class HelloWorld implements Servlet {
    @Override
    public void doGet(Request request, Response response) {
        response.setStatus(HTTPStatus.OK);
        response.setBody("Hello World");
        response.writeToBrowser();
    }

    @Override
    public void doPost(Request request, Response response) {

    }
}
