package com.lkm.webserver;

import com.lkm.webserver.connection.ConnectionCleaner;
import com.lkm.webserver.connection.Processor;
import com.lkm.webserver.servlet.LoadServlet;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getGlobal().info("Start loading servlet(s)");
        Processor.servletList = LoadServlet.buildServletMap();

        Logger.getGlobal().info("Starting server");
        Server server = new Server();
        Processor.serverExecutor.execute(server::run);

        Logger.getGlobal().info("Starting cleaners");
        Processor.serverExecutor.execute(() -> {
            try {
                ConnectionCleaner.clearSocket();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Processor.serverExecutor.execute(() -> {
            try {
                ConnectionCleaner.clearSession();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
