package com.storage;

import com.storage.web.servlet.BrandServlet;
import com.storage.web.servlet.MainServlet;
import com.storage.web.servlet.SouvenirServlet;
import com.storage.web.servlet.StaticFileServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {

        ServletContextHandler contextHandler =
                new ServletContextHandler(
                        ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new MainServlet()), "/");
        contextHandler.addServlet(new ServletHolder(new BrandServlet()), "/brand");
        contextHandler.addServlet(new ServletHolder(new SouvenirServlet()), "/souvenir");

        contextHandler.setContextPath("/");
        contextHandler.setResourceBase("src/main/webapp");
        contextHandler.addServlet(StaticFileServlet.class, "/static/*");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        try {
            server.start();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
