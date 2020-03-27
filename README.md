# WSX - A Simple WebServer written in Java

A multi-thread webserver, based on NIO and simplified HTTP protocol is supported.

## Feature

- [x] GET and POST Request Method
- [x] multipart-form request
- [x] Cookie and Session support
- [x] Servlet
- [x] Static resources processing
- [x] URL patten match
- [x] Keep Alive
- [x] Partial Content

## Usage

**Java 1.8 or higher is required**

1. Edit `constant/Misc.java` to modify configuration
2. Servlet class in `WWW_CLASSES` will be auto loaded
3. Write your own Servlet and compile it and move .class files to `WWW_CLASSES`
4. Open `http://127.0.0.1:8080`

## How to write a Servlet
```java
@UrlMatch(urlMatch = "/path")
public class YourServlet implements Servlet {
    @Override
    public void doGet(Request request, Response response) {
        response.setStatus(HTTPStatus.OK);
        response.setBody("Hello World!!");
        response.writeToBrowser();
    }

    @Override
    public void doPost(Request request, Response response) {
    }
}
```
Enjoy it.

## Licence
MIT