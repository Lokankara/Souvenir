package com.storage.web.servlet;

import com.storage.model.dto.PostBrandDto;
import com.storage.service.IOBrandService;
import com.storage.web.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.storage.web.Pagination.getParams;

public class BrandServlet extends HttpServlet {
    private final IOBrandService service;
    private final PageGenerator pageGenerator;

    public BrandServlet() {
        this.service = new IOBrandService();
        this.pageGenerator = PageGenerator.instance();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        List<PostBrandDto> brands = service.findAll();
        Map<String, Object> params =
                getParams(req, "brands", brands);
        resp.getWriter().write(pageGenerator
                .getPage("brands.html", params));
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        PostBrandDto dto = getBrandFromRequest(req);
        PostBrandDto save = service.save(dto);
        resp.sendRedirect("/brand");
    }

    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        PostBrandDto dto = getBrandFromRequest(req);
        PostBrandDto edit = service.edit(dto);
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        service.delete(name);
        resp.sendRedirect("/");
    }

    private PostBrandDto getBrandFromRequest(
            HttpServletRequest req) {
        String brand = req.getParameter("brand");
        String country = req.getParameter("country");
        return PostBrandDto.builder()
                    .name(brand)
                    .country(country)
                    .build();
    }
}
