package com.shop.storage.service.mapper;

import com.opencsv.bean.AbstractBeanField;
import com.shop.storage.model.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter
        extends AbstractBeanField<Brand, String> {
    @Override
    protected Object convert(String value) {
        String[] values = value.split(",");
        return new Brand(values[0], values[1]);
    }
}
