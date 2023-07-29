package com.storage.web.servlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaticFileServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletConfig servletConfig;

    @Test
    void testDoGet() throws Exception {
        when(request.getPathInfo()).thenReturn("/script.js");
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/WEB-INF/static/script.js"))
                .thenReturn("src/test/resources/script.log");
        when(servletContext.getMimeType("script.log"))
                .thenReturn("text/plain");
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        File testFile = new File("src/test/resources/script.log");
        Files.write(testFile.toPath(), "test content".getBytes());

        StaticFileServlet servlet = new StaticFileServlet();
        servlet.init(servletConfig);
        servlet.doGet(request, response);

        int cssCount = 0;
        int jsCount = 0;
        int htmlCount = 0;

        File staticDir = new File("src/main/webapp/WEB-INF/static");
        File[] files = staticDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.endsWith(".css")) {
                    cssCount++;
                } else if (filename.endsWith(".js")) {
                    jsCount++;
                } else if (filename.endsWith(".html")) {
                    htmlCount++;
                }
            }
        } else {
            System.err.println("Error: Could not list files in directory " + staticDir);
        }

        File logFile = new File("src/test/resources/script.log");
        try (PrintWriter writer = new PrintWriter(logFile)) {
            writer.println("CSS files: " + cssCount);
            writer.println("JS files: " + jsCount);
            writer.println("HTML files: " + htmlCount);
        }

        verify(response).setContentType("text/plain");
    }
}
