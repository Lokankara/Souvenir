package com.shop.storage.web;

import com.shop.storage.model.entity.Brand;
import com.shop.storage.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping("/brands")
    public String showBrands(Model model) {
        List<Brand> brands = brandService.findAllBrandWithSouvenirs();
        model.addAttribute("brands", brands);
        return "brands";
    }
}
