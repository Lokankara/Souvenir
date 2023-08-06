package com.storage.web.servlet;

import com.storage.model.dto.PostBrandDto;
import com.storage.model.entity.Option;
import com.storage.service.IOBrandService;
import com.storage.web.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.storage.web.Pagination.getParams;

public class BrandServlet extends HttpServlet {

    private final IOBrandService service;
    private final PageGenerator pageGenerator;
    private static final String ERROR = "/error";
    private static final Logger LOGGER = Logger.getLogger(BrandServlet.class.getName());

    public BrandServlet() {
        this.service = new IOBrandService();
        this.pageGenerator = PageGenerator.instance();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        List<PostBrandDto> brands = new ArrayList<>();
        String query = req.getParameter("query");
        if (query != null) {
            String option = req.getParameter("option");
            if (Option.BRAND.name()
                            .equalsIgnoreCase(option)) {
                brands = service.findAllByName(query);
            } else if (Option.COUNTRY.name()
                                     .equalsIgnoreCase(option)) {
                brands = service.findAllByCountry(query);
            }
        } else {
            brands = service.findAll();
        }
        try {
        resp.getWriter()
            .write(pageGenerator.getPage("brands.html",
                    getParams(req, "brands", brands)));
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
            PostBrandDto dto = getBrandFromRequest(req);
            PostBrandDto save = service.save(dto);
            LOGGER.info(save.toString());
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
            PostBrandDto dto = getBrandFromRequest(req);
            PostBrandDto edit = service.update(dto);
            LOGGER.info(edit.toString());
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            req.setAttribute("errorMessage", e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }

    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp) {
        String name = req.getParameter("name");
        service.delete(name);
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
