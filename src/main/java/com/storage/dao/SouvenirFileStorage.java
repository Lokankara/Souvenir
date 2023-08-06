package com.storage.dao;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.SouvenirIsAlreadyExists;
import com.storage.service.exception.SouvenirNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class SouvenirFileStorage implements FileStorage<Souvenir> {

    private static final Logger LOGGER = Logger.getLogger(SouvenirFileStorage.class.getName());
    private static final String SOUVENIR_PATH = "src/main/resources/data/souvenir.csv";
    private static final String HEADER_CSV = "COUNTRY,BRAND,ISSUE,NAME,PRICE";
    private static final String[] FIELDS = {"country", "brand", "issue", "name", "price"};

    @Override
    public Souvenir saveToCsv(Souvenir souvenir) {
        try (FileWriter writer = new FileWriter(SOUVENIR_PATH, true)) {
            if (isWriteHeader(SOUVENIR_PATH, HEADER_CSV)) {
                writer.write(HEADER_CSV + "\n");
            }
            if (!isDuplicate(souvenir)) {
                writer.write(String.join(",",
                        getData(souvenir)) + "\n");
                return souvenir;
            } else {
                String msg = "Duplicate entry, not saved.";
                LOGGER.warning(msg);
                throw new SouvenirIsAlreadyExists(msg);
            }
        } catch (IOException e) {
            String msg = "could not save souvenir from csv ";
            LOGGER.warning(msg + SOUVENIR_PATH);
            throw new SouvenirNotFoundException(msg);
        }
    }

    @Override
    public List<Souvenir> readFromCsv(String path) {
        try (Reader reader = new FileReader(path)) {
            ColumnPositionMappingStrategy<Souvenir> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Souvenir.class);
            strategy.setColumnMapping(FIELDS);
            return new CsvToBeanBuilder<Souvenir>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (IOException e) {
            String msg = "could not read souvenirs from csv ";
            LOGGER.warning(msg + SOUVENIR_PATH);
            throw new SouvenirNotFoundException(msg);
        }
    }

    @Override
    public Souvenir updateCsv(Souvenir souvenir) {
        List<Souvenir> souvenirs = readFromCsv(SOUVENIR_PATH);
        IntStream.range(0, souvenirs.size())
                 .filter(i -> souvenirs
                         .get(i)
                         .getName()
                         .equals(souvenir.getName()))
                 .findFirst()
                 .ifPresent(s -> souvenirs.set(s, souvenir));
        writeToCsvFile(souvenirs, SOUVENIR_PATH, FIELDS);
        return souvenir;
    }

    @Override
    public void deleteFromCsv(String name) {
        List<Souvenir> souvenirs = readFromCsv(SOUVENIR_PATH);
        souvenirs.removeIf(souvenir -> souvenir
                .getName()
                .equals(name));
        writeToCsvFile(souvenirs, SOUVENIR_PATH, FIELDS);
    }

    public void deleteFromCsvByBrand(String brandName) {
        List<Souvenir> souvenirs = readFromCsv(SOUVENIR_PATH);
        souvenirs.removeIf(souvenir -> souvenir
                .getBrand()
                .getName()
                .equals(brandName));
        writeToCsvFile(souvenirs, SOUVENIR_PATH, FIELDS);
    }

    @Override
    public String[] getData(Souvenir souvenir) {
        return new String[]{
                souvenir.getBrand().getCountry(),
                souvenir.getBrand().getName(),
                souvenir.getIssue().toString(),
                souvenir.getName(),
                souvenir.getPrice().toString()};
    }

    private boolean isDuplicate(Souvenir souvenir) {
        return readFromCsv(SOUVENIR_PATH)
                .contains(souvenir);
    }
}
