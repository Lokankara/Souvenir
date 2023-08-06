package com.storage;

import com.storage.web.servlet.BrandServlet;
import com.storage.web.servlet.SouvenirServlet;
import com.storage.web.servlet.StaticFileServlet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static final int PORT = 8080;
    private static final String BASE_URL = "http://localhost:" + PORT;

    private static Server server;

    @BeforeEach
    public void setup() throws Exception {
        server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new BrandServlet()), "/brand");
        contextHandler.addServlet(new ServletHolder(new SouvenirServlet()), "/");
        contextHandler.setContextPath("/");
        contextHandler.setResourceBase("src/main/webapp");
        contextHandler.addServlet(StaticFileServlet.class, "/static/*");
        server.setHandler(contextHandler);
        server.start();
    }

    @AfterEach
    public void teardown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }

    @Test
    void testMainServlet() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/");
            CloseableHttpResponse response = client.execute(request);
            assertEquals(200, response.getStatusLine()
                                      .getStatusCode());
        }
    }

    @Test
    void testBrandServlet() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/brand");
            CloseableHttpResponse response = client.execute(request);
            assertEquals(200, response.getStatusLine()
                                      .getStatusCode());
        }
    }

    @Test
    void testSouvenirServlet() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/");
            CloseableHttpResponse response = client.execute(request);
            assertEquals(200, response.getStatusLine()
                                      .getStatusCode());
        }
    }
}