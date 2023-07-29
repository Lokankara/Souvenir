package com.storage.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import com.storage.model.entity.Brand;
import com.storage.service.exception.BrandNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class BrandFileStorage implements FileStorage<Brand> {

    private static final Logger LOGGER = Logger.getLogger(BrandFileStorage.class.getName());
    private static final String BRAND_PATH = "src/main/resources/data/brand.csv";
    private static final String HEADER_CSV_BRAND = "COUNTRY,NAME";
    private static final String[] FIELDS = {"country", "name"};
    private static final String MESSAGE = "Could not save Brands From Csv ";

    @Override
    public Brand saveToCsv(Brand brand) {
        try (FileWriter writer = new FileWriter(BRAND_PATH, true)) {
            if (isWriteHeader(BRAND_PATH, HEADER_CSV_BRAND)) {
                writer.write(HEADER_CSV_BRAND + "\n");
            }
            writer.write(getLine(brand));
            return brand;
        } catch (IOException e) {
            LOGGER.warning(MESSAGE + BRAND_PATH);
            throw new BrandNotFoundException(MESSAGE + brand.getName());
        }
    }

    @Override
    public List<Brand> readFromCsv(String path) {
        try (Reader reader = new FileReader(path)) {
            return new CsvToBeanBuilder<Brand>(reader)
                    .withType(Brand.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            LOGGER.warning("Could not read brands from csv "
                    + path);
            throw new BrandNotFoundException(MESSAGE + path);
        }
    }

    @Override
    public Brand updateCsv(Brand brand) {
        List<Brand> brands = readFromCsv(BRAND_PATH);
        for (int i = 0; i < brands.size(); i++) {
            if (brands.get(i)
                      .getName()
                      .equals(brand.getName())) {
                brands.set(i, brand);
            }
        }
        writeToCsvFile(brands, BRAND_PATH, FIELDS);
        return brand;
    }

    @Override
    public void deleteFromCsv(String name) {
        List<Brand> brands = readFromCsv(BRAND_PATH);
        brands.removeIf(brand -> brand
                .getName()
                .equals(name));
        writeToCsvFile(brands, BRAND_PATH, FIELDS);
    }

    @Override
    public String[] getData(Brand brand) {
        return new String[]{
                brand.getCountry(),
                brand.getName()
        };
    }

    private String getLine(Brand brand) {
        return String.join(",",
                brand.getCountry(),
                brand.getName() + "\n");
    }
}
