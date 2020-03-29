package com.lkm.webserver.connection;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lkm.webserver.api.Servlet;
import com.lkm.webserver.constant.Misc;
import com.lkm.webserver.constant.RequestMethod;
import com.lkm.webserver.exception.ContentLengthError;
import com.lkm.webserver.request.Request;
import com.lkm.webserver.request.RequestParser;
import com.lkm.webserver.response.Response;
import com.lkm.webserver.servlet.DefaultServlet;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.*;

public class Processor {
    public static Map<String, Servlet> servletList;
    private static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Request Processor Thread")
            .build();
    public static final ExecutorService requestExecutor =
            new ThreadPoolExecutor(10,
                    Misc.MAX_CONNECTION_POOL_SIZE,
                    Misc.EXPIRES_TIME,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(),
                    threadFactory);

    private static final ThreadFactory serverThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Server and cleaner Thread")
            .build();

    public static final ExecutorService serverExecutor =
            new ThreadPoolExecutor(0,
                    Misc.MAX_CONNECTION_POOL_SIZE,
                    Misc.EXPIRES_TIME,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<>(),
                    serverThreadFactory);

    public static void processRequest(Connection connection) {
        if (connection == null || connection.isRunning() || connection.getHttpMessage().size() == 0) {
            return;
        }
        connection.setRunning(true);
        SocketChannel socketChannel = connection.getSocketChannel();
        if (!socketChannel.isConnected()) {
            return;
        }

        try {
            Request request = RequestParser.parseMessage(connection);
            connection.setRequest(request);
            if (servletList.containsKey(request.getRequestLine().getPath())) {
                Servlet servlet = servletList.get(request.getRequestLine().getPath());
                if (request.getRequestLine().getMethod() == RequestMethod.GET) {
                    servlet.doGet(request, new Response(socketChannel));
                } else if (request.getRequestLine().getMethod() == RequestMethod.POST) {
                    servlet.doPost(request, new Response(socketChannel));
                }
            } else {
                DefaultServlet servlet = new DefaultServlet();
                servlet.doGet(request, new Response(socketChannel));
            }
            connection.setRunning(false);
            if (!"keep-alive".equals(request.getHeader("connection").toLowerCase())) {
                ConnectionPool.removeSocket(socketChannel);
            }
        } catch (ContentLengthError e) {
            e.printStackTrace();
            try {
                connection.getSocketChannel().close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
