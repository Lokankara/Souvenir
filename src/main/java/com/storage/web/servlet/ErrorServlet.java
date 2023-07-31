package com.storage.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorServlet extends HttpServlet {
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
            IOException {
        String errorMessage = (String) request
                .getAttribute("javax.servlet.error.message");
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/WEB-INF/static/error.html")
               .forward(request, response);
    }
}