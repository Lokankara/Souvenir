package com.shop.storage.dao.io;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.shop.storage.dao.FileProvider;
import com.shop.storage.model.entity.Brand;
import com.shop.storage.model.entity.Souvenir;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CsvProvider implements FileProvider {

    private static final String HEADER_CSV_BRAND = "COUNTRY,NAME";
    private static final String HEADER_CSV = "BRAND,COUNTRY,ISSUE,NAME,PRICE";
    private static final String BRAND_PATH = "src/main/resources/data/brand.csv";
    private static final String SOUVENIR_PATH = "src/main/resources/data/souvenir.csv";
    private static final String[] FIELDS = {"brand", "country", "issue", "name", "price"};

    @Override
    public Optional<Souvenir> saveToCsv(Souvenir souvenir) {
        try (FileWriter writer = new FileWriter(SOUVENIR_PATH, true)) {
            if (isWriteHeader(SOUVENIR_PATH, HEADER_CSV)) {
                writer.write(HEADER_CSV + "\n");
            }
            writer.write(getLine(souvenir));
            return Optional.of(souvenir);
        } catch (IOException e) {
            log.error("could not save Souvenir From Csv");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Brand> saveToCsv(Brand brand) {
        try (FileWriter writer = new FileWriter(BRAND_PATH, true)) {
            if (isWriteHeader(BRAND_PATH, HEADER_CSV_BRAND)) {
                writer.write(HEADER_CSV_BRAND + "\n");
            }
            writer.write(getLine(brand));
            return Optional.of(brand);
        } catch (IOException e) {
            log.error("could not save Brands From Csv");
            return Optional.empty();
        }
    }

    @Override
    public List<Souvenir> readSouvenirsFromCsv() {
        List<Souvenir> souvenirs = new ArrayList<>();
        try (Reader reader = new FileReader(SOUVENIR_PATH)) {
            ColumnPositionMappingStrategy<Souvenir> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Souvenir.class);
            strategy.setColumnMapping(FIELDS);
            souvenirs = new CsvToBeanBuilder<Souvenir>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (IOException e) {
            log.error("could not be parsed");
        }
        return souvenirs;
    }

    @Override
    public List<Brand> readBrandsFromCsv() {
        List<Brand> brands = new ArrayList<>();
        try (Reader reader = new FileReader(BRAND_PATH)) {
            brands = new CsvToBeanBuilder<Brand>(reader)
                    .withType(Brand.class)
                    .build()
                    .parse();
        } catch (IOException e) {
            log.error("could not read Brands From Csv");
        }
        return brands;
    }

    @Override
    public Optional<Souvenir> deleteSouvenir(String name) {
        Optional<Souvenir> result = Optional.empty();
        List<Souvenir> souvenirs = readSouvenirsFromCsv();
        //        souvenirs.removeIf(souvenir -> souvenir.getName().equals(name));
        for (Souvenir souvenir : souvenirs) {
            if (souvenir.getName().equals(name)) {
                souvenirs.remove(souvenir);
                result = Optional.of(souvenir);
            }
        }

        try (FileWriter fileWriter = new FileWriter(SOUVENIR_PATH);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            csvWriter.writeNext(FIELDS);

            for (Souvenir souvenir : souvenirs) {
                String[] data = getData(souvenir);
                csvWriter.writeNext(data);
            }
            log.info("Souvenir with name '{}' was deleted and CSV file updated.", name);
        } catch (IOException e) {
            log.error("Error occurred while updating the CSV file: {}", SOUVENIR_PATH);
        }
        return result;
    }

    private static boolean isWriteHeader(
            String filePath,
            String header) {
        boolean writeHeader = true;
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {
                if (header.equals(reader.readLine())) {
                    writeHeader = false;
                }
            } catch (IOException e) {
                log.error("could not read isWriteHeader");
            }
        }
        return writeHeader;
    }

    private String getLine(Souvenir souvenir) {
        return String.join(",", getData(souvenir)) + "\n";
    }

    private String getLine(Brand brand) {
        return String.join(",",
                brand.getCountry(),
                brand.getName() + "\n");
    }

    private String[] getData(Souvenir souvenir) {
        return new String[]{
                souvenir.getBrand().getName(),
                souvenir.getBrand().getCountry(),
                souvenir.getIssue().toString(),
                souvenir.getName(),
                souvenir.getPrice().toString()
        };
    }
}
