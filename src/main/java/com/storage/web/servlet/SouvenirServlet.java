package com.storage.web.servlet;

import com.storage.model.dto.Dto;
import com.storage.model.dto.PostBrandDto;
import com.storage.model.dto.PostSouvenirDto;
import com.storage.model.entity.Option;
import com.storage.service.IOSouvenirService;
import com.storage.service.exception.DataInputException;
import com.storage.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.storage.web.Pagination.getParams;

public class SouvenirServlet extends HttpServlet {

    private static final String ERROR = "/error";
    private static final String SOUVENIR = "/";
    private final IOSouvenirService service;
    private final PageGenerator pageGenerator;
    private static final Logger LOGGER = Logger.getLogger(SouvenirServlet.class.getName());

    public SouvenirServlet() {
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
            if (Option.BRAND.name()
                            .equalsIgnoreCase(option)) {
                souvenirs = service.findAllByBrand(query);
            } else if (Option.YEAR.name()
                                  .equalsIgnoreCase(option)) {
                souvenirs = service.findAllByYear(query);
            } else if (Option.COUNTRY.name()
                                     .equalsIgnoreCase(option)) {
                souvenirs = service.findAllByCountry(query);
            } else if (Option.PRICE.name()
                                   .equalsIgnoreCase(option)) {
                souvenirs = service.findAllByPrice(query);
            }
        } else {
            souvenirs = service.findAll();
        }
        try {
            resp.getWriter()
                .write(pageGenerator.getPage("index.html",
                        getParams(req, "souvenirs", souvenirs)));
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        try {
            saveDtoRequest(req);
            resp.sendRedirect(SOUVENIR);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }

    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        try {
            updateDtoRequest(req);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        try {
            LOGGER.info(req.getHeader("Referer"));
            service.delete(req.getParameter("name"));
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }

    private void updateDtoRequest(
            HttpServletRequest req)
            throws DataInputException {
        PostSouvenirDto dto = getPostSouvenirDto(req,
                getPostBrandDto(req));
        service.update(dto);
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
        String brand = req.getParameter("brand") == null
                ? "" : req.getParameter("brand");
        String country = req.getParameter("country") == null
                ? "" : req.getParameter("country");
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
        String parameter = req.getParameter("price");
        Double price = Double.valueOf(parameter.replace(",", ""));
        LocalDateTime issue = LocalDateTime.parse(req.getParameter("issue"));
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
