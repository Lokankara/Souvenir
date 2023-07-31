package com.storage.web.servlet;

import com.storage.model.dto.Dto;
import com.storage.model.entity.Option;
import com.storage.service.IOSouvenirService;
import com.storage.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.storage.web.Pagination.getParams;

public class MainServlet extends HttpServlet {

    private final IOSouvenirService service;
    private final PageGenerator pageGenerator;

    public MainServlet() {
        this.service = new IOSouvenirService();
        this.pageGenerator = PageGenerator.instance();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        List<? extends Dto> souvenirs = new ArrayList<>();
        String query = req.getParameter("query");
        if (query != null) {
            String option = req.getParameter("option");
            if (Option.BRAND.name().equalsIgnoreCase(option)) {
                souvenirs = service.findAllByBrand(query);
            } else if (Option.YEAR.name().equalsIgnoreCase(option)) {
                souvenirs = service.findAllByYear(query);
            } else if (Option.COUNTRY.name().equalsIgnoreCase(option)) {
                souvenirs = service.findAllByCountry(query);
            }
        } else {
            souvenirs = service.findAll();
        }
        String referer = req.getHeader("Referer");
        System.out.println(referer);

        Map<String, Object> params = getParams(req, "souvenirs", souvenirs);
        resp.getWriter().write(pageGenerator.getPage("index.html", params));
    }
}