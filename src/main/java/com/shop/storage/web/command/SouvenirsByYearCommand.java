package com.shop.storage.web.command;

import com.shop.storage.service.IOBrandService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SouvenirsByYearCommand implements Command {

    private final IOBrandService service;

    @Override
    public String execute() {
        return "souvenirs";
    }
}
