package com.storage.web.servlet;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.service.IOSouvenirService;
import com.storage.service.exception.DataInputException;
import com.storage.web.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static com.storage.web.Pagination.getParams;

public class SouvenirServlet extends HttpServlet {
    private static final String SOUVENIR = "/souvenir";
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
        Map<String, Object> params = getParams(
                req, "souvenirs", service.findAll());
        resp.getWriter()
            .write(pageGenerator.getPage(
                    "souvenirs.html", params));
    }

    @Override
    @SneakyThrows
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) {
        try {
            saveDtoRequest(req);
            resp.sendRedirect(SOUVENIR);
        } catch (Exception e) {
            resp.sendRedirect("/error");
        }
    }

    @Override
    @SneakyThrows
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp) {
        try {
            updateDtoRequest(req);
            resp.sendRedirect(SOUVENIR);
        } catch (Exception e) {
            resp.sendRedirect("/error");
        }
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        service.delete(req.getParameter("name"));
        resp.sendRedirect("/");
    }

    private void updateDtoRequest(
            HttpServletRequest req)
            throws DataInputException {
        service.update(getPostSouvenirDto(
                req, getPostBrandDto(req)));
    }

    private void saveDtoRequest(
            HttpServletRequest req)
            throws DataInputException {
        service.save(getPostSouvenirDto(
                req, getPostBrandDto(req)));
    }

    private static PostBrandDto getPostBrandDto(
            HttpServletRequest req)
            throws DataInputException {
        String brand = req.getParameter("brand");
        String country = req.getParameter("country");
        return getBrand(brand, country);
    }

    private static PostBrandDto getBrand(
            String brand, String country) {
        return PostBrandDto
                .builder()
                .name(brand)
                .country(country)
                .build();
    }

    private static PostSouvenirDto getPostSouvenirDto(
            HttpServletRequest req,
            PostBrandDto brandDto) {
        String name = req.getParameter("name");
        Double price = Double.valueOf(
                req.getParameter("price")
                   .replace(",", ""));
        LocalDateTime issue = LocalDateTime.parse(
                req.getParameter("issue"));
        return getSouvenir(name, price, issue, brandDto);
    }

    private static PostSouvenirDto getSouvenir(
            final String name,
            final Double price,
            final LocalDateTime issue,
            final PostBrandDto postBrands) {
        return PostSouvenirDto
                .builder()
                .brand(postBrands)
                .issue(issue)
                .name(name)
                .price(price)
                .build();
    }
}
