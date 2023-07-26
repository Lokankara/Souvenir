package com.shop.storage.web;

import com.shop.storage.model.dto.PostBrandDto;
import com.shop.storage.model.dto.PostSouvenirDto;
import com.shop.storage.service.IOSouvenirService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SouvenirController {

    @Value("${page.size}")
    private int pageSize;

    private final IOSouvenirService souvenirService;

    @GetMapping
    public String showSouvenirs(
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        List<PostSouvenirDto> souvenirs = souvenirService.findAll();
        int offset = Math.min((page - 1) * pageSize, souvenirs.size());
        int toIndex = Math.min(offset + pageSize, souvenirs.size());
        int totalPages = (int) Math.ceil((double) souvenirs.size() / pageSize);
        List<PostSouvenirDto> rangeSouvenirs = souvenirs.subList(offset, toIndex);
        log.info("page: " + page + ", toIndex " + toIndex + ", totalPages: " + totalPages + ", offset: " + offset);
        model.addAttribute("souvenirs", rangeSouvenirs);
        model.addAttribute("pageNumbers", totalPages);
        model.addAttribute("currentPage", page);
        return "souvenirs";
    }

    @PostMapping("/create")
    public String createSouvenir(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String brand,
            @RequestParam String country,
            @RequestParam LocalDateTime issue,
            RedirectAttributes attributes) {
        PostSouvenirDto dto = getSouvenir(name, price, issue, getBrand(brand, country));
        PostSouvenirDto save = souvenirService.save(dto);
        attributes.addFlashAttribute("alert", save); //TODO
        return "redirect:/";
    }

    @DeleteMapping("/delete/{name}")
    public String deleteSouvenir(
            @PathVariable String name,
            RedirectAttributes attributes) {
        PostSouvenirDto delete = souvenirService.delete(name);
        attributes.addFlashAttribute("alert", delete); //TODO
        return "redirect:/";
    }

    @PatchMapping("/update/{name}")
    public String updateSouvenir(
            @PathVariable String name,
            @RequestParam double price,
            @RequestParam String brand,
            @RequestParam String country,
            @RequestParam LocalDateTime issue,
            RedirectAttributes attributes) {
        PostSouvenirDto dto = getSouvenir(name, price, issue, getBrand(brand, country));
        PostSouvenirDto edit = souvenirService.edit(dto);
        attributes.addFlashAttribute("alert", edit);//TODO
        return "redirect:/";
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
