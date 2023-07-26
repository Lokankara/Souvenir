package com.storage.service.mapper;

import com.opencsv.bean.AbstractBeanField;
import com.storage.model.entity.Brand;

public class BrandConverter
        extends AbstractBeanField<Brand, String> {
    @Override
    protected Object convert(String value) {
        String[] values = value.split(",");
        return new Brand(values[1], values[0]);
    }
}
