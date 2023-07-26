package com.storage;

import com.storage.web.MainServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args)
            throws Exception {

        ServletContextHandler contextHandler =
                new ServletContextHandler(
                ServletContextHandler.SESSIONS);

        contextHandler.addServlet(
                new ServletHolder(
                        new MainServlet()), "/souvenir");

        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
    }
}
