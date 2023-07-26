package com.storage.web;


import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.service.IOSouvenirService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainServlet
        extends HttpServlet {

    private static final int SIZE = 8;

    private final IOSouvenirService service;
    private final PageGenerator pageGenerator;

    public MainServlet() {
        this.service = new IOSouvenirService();
        this.pageGenerator = PageGenerator.instance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> params = new HashMap<>();
        String pageParam = req.getParameter("page");
        int page = pageParam != null ? Integer.parseInt(pageParam) : 0;

        List<PostSouvenirDto> souvenirs = service.findAll();
        int totalPages = (int) Math.ceil((double) souvenirs.size() / SIZE);
        page = Math.min(totalPages, Math.max(1, page));
        int offset = Math.abs(Math.min((page - 1) * SIZE, souvenirs.size()));
        int toIndex = Math.min(offset + SIZE, souvenirs.size());

        params.put("souvenirs", offset <= toIndex
                ? souvenirs.subList(offset, toIndex)
                : new ArrayList<PostSouvenirDto>());

        params.put("totalPages", totalPages);
        params.put("currentPage", page);

        resp.getWriter().write(
                pageGenerator.getPage(
                        "index.html", params));
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        PostSouvenirDto dto = getSouvenirFromRequest(req);
        PostSouvenirDto save = service.save(dto); //TODO
        resp.sendRedirect("/souvenir");
    }

    private static PostSouvenirDto getSouvenirFromRequest(
            HttpServletRequest req) {
        String name = req.getParameter("name");
        Double price = Double.valueOf(req.getParameter("price")
                                         .replace(",", ""));
        String brand = req.getParameter("brand");
        String country = req.getParameter("country");
        LocalDateTime issue = LocalDateTime.parse(req.getParameter("issue"));
        return getSouvenir(name, price, issue, getBrand(brand, country));
    }

    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        PostSouvenirDto dto = getSouvenirFromRequest(req);
        PostSouvenirDto edit = service.edit(dto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        PostSouvenirDto delete = service.delete(
                name);  //TODO
        resp.sendRedirect("/");
    }

    private static PostSouvenirDto getSouvenir(
            String name,
            Double price,
            LocalDateTime issue,
            PostBrandDto postBrand) {
        return PostSouvenirDto.builder()
                              .brand(postBrand)
                              .issue(issue)
                              .name(name)
                              .price(price)
                              .build();
    }

    private static PostBrandDto getBrand(
            String brand, String country) {
        return PostBrandDto.builder()
                           .name(brand)
                           .country(country)
                           .build();
    }
}
