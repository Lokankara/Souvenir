package com.storage.web.servlet;

import com.storage.service.IOBrandService;
import com.storage.web.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.storage.web.Pagination.getParams;

public class ErrorServlet extends HttpServlet {
    private final PageGenerator pageGenerator;

    public ErrorServlet() {
        this.pageGenerator = PageGenerator.instance();
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
            IOException {
        Map<String, Object> params = new HashMap<>();
        response.getWriter().write(pageGenerator.getPage("error.html", params));
    }
}
