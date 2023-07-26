package com.storage.dao.storage;

import com.opencsv.bean.CsvToBeanBuilder;
import com.storage.model.entity.Brand;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class BrandFileStorage implements FileStorage<Brand> {

    private static final Logger LOGGER = Logger.getLogger(BrandFileStorage.class.getName());
    private static final String HEADER_CSV_BRAND = "COUNTRY,NAME";
    private static final String BRAND_PATH = "src/main/resources/data/brand.csv";
    @Override
    public Optional<Brand> saveToCsv(Brand brand) {
        try (FileWriter writer = new FileWriter(BRAND_PATH, true)) {
            if (isWriteHeader(BRAND_PATH, HEADER_CSV_BRAND)) {
                writer.write(HEADER_CSV_BRAND + "\n");
            }
            writer.write(getLine(brand));
            return Optional.of(brand);
        } catch (IOException e) {
            LOGGER.warning("Could not save Brands From Csv");
            return Optional.empty();
        }
    }

    @Override
    public List<Brand> readFromCsv() {
        List<Brand> brands = new ArrayList<>();
        try (Reader reader = new FileReader(BRAND_PATH)) {
            brands = new CsvToBeanBuilder<Brand>(reader)
                    .withType(Brand.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            LOGGER.warning("Could not read Brands From Csv");
        }
        return brands;
    }

    @Override
    public Optional<Brand> update(Brand brand) {
        return Optional.empty();
    }

    @Override
    public Optional<Brand> deleteFromCsv(String name) {
        return Optional.empty();
    }

    private String getLine(Brand brand) {
        return String.join(",",
                brand.getCountry(),
                brand.getName() + "\n");
    }
}
