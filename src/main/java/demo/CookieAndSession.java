package demo;

import com.lkm.webserver.api.HttpRequest;
import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.response.Cookie;
import com.lkm.webserver.servlet.UrlMatch;

@UrlMatch(urlMatch = "/cookieandsession")
public class CookieAndSession implements Servlet {
    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.startSession(request);
        response.setSession("session", "sessionTest");
        response.setCookies("cookie1", "cookie1");
        response.setCookies(new Cookie.Builder("cookie2", "cookie2").maxAge(96335).build());

        StringBuilder stringBuilder = new StringBuilder("Session: ")
                .append(request.getSession("session")).append("\n");
        stringBuilder.append("cookie1: ").append(request.getCookie("cookie1")).append("\n");
        stringBuilder.append("cookie2: ").append(request.getCookie("cookie2"));

        response.setBody(stringBuilder.toString());
        response.setStatus(HTTPStatus.OK);
        response.writeToBrowser();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
