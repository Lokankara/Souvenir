package com.storage.web.servlet;

import com.storage.model.dto.Dto;
import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.service.IOSouvenirService;
import com.storage.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.storage.web.Pagination.getParams;

public class SouvenirServlet extends HttpServlet {

    private final IOSouvenirService service;
    private final PageGenerator pageGenerator;

    public SouvenirServlet() {
        this.service = new IOSouvenirService();
        this.pageGenerator = PageGenerator.instance();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        List<? extends Dto> souvenirs = service.findAll();
        Map<String, Object> params =
                getParams(req, "souvenirs", souvenirs);
        resp.getWriter()
            .write(pageGenerator.getPage("souvenirs.html", params));
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        PostSouvenirDto dto = getSouvenirFromRequest(req);
        PostSouvenirDto save = service.save(dto);
        resp.sendRedirect("/souvenir");
    }

    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp) {
        PostSouvenirDto dto = getSouvenirFromRequest(req);
        PostSouvenirDto edit = service.edit(dto);
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        service.delete(name);
        resp.sendRedirect("/");
    }

    private static PostSouvenirDto getSouvenirFromRequest(
            HttpServletRequest req) {
        String name = req.getParameter("name");
        Double price = Double.valueOf(
                req.getParameter("price")
                   .replace(",", ""));
        String brand = req.getParameter("brand");
        String country = req.getParameter("country");
        LocalDateTime issue = LocalDateTime
                .parse(req.getParameter("issue"));
        return getSouvenir(name, price, issue, getBrand(brand, country));
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
