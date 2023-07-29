package com.storage.dao;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.storage.model.entity.Souvenir;
import com.storage.service.exception.SouvenirIsAlreadyExists;
import com.storage.service.exception.SouvenirNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class SouvenirFileStorage implements FileStorage<Souvenir> {

    private static final Logger LOGGER = Logger.getLogger(SouvenirFileStorage.class.getName());
    private static final String SOUVENIR_PATH = "src/main/resources/data/souvenir.csv";
    private static final String HEADER_CSV = "BRAND,COUNTRY,ISSUE,NAME,PRICE";
    private static final String[] FIELDS = {"brand", "country", "issue", "name", "price"};

    @Override
    public Souvenir saveToCsv(Souvenir souvenir) {
        try (FileWriter writer = new FileWriter(SOUVENIR_PATH, true)) {
            if (isWriteHeader(SOUVENIR_PATH, HEADER_CSV)) {
                writer.write(HEADER_CSV + "\n");
            }
            if (!isDuplicate(souvenir)) {
                writer.write(getLine(souvenir));
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
        for (int i = 0; i < souvenirs.size(); i++) {
            if (souvenirs.get(i)
                         .getName()
                         .equals(souvenir.getName())) {
                souvenirs.set(i, souvenir);
                return souvenir;
            }
        }
        writeToCsvFile(souvenirs, SOUVENIR_PATH, FIELDS);
        return souvenir;
    }

    @Override
    public void deleteFromCsv(String name) {
        List<Souvenir> souvenirs = readFromCsv(SOUVENIR_PATH);
        souvenirs.removeIf(souvenir -> souvenir
                .getName().equals(name));
        writeToCsvFile(souvenirs, SOUVENIR_PATH, FIELDS);
    }

    private String getLine(Souvenir souvenir) {
        return String.join(",",
                getData(souvenir)) + "\n";
    }

    @Override
    public String[] getData(Souvenir souvenir) {
        return new String[]{
                souvenir.getBrand().getName(),
                souvenir.getBrand().getCountry(),
                souvenir.getIssue().toString(),
                souvenir.getName(),
                souvenir.getPrice().toString()};
    }

    private boolean isDuplicate(Souvenir souvenir) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(SOUVENIR_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(souvenir
                        .getName())
                        && values[1].equals(souvenir
                        .getBrand()
                        .getCountry())
                        && values[2].equals(souvenir
                        .getBrand()
                        .getCountry())) {
                    return true;
                }
            }
        } catch (IOException e) {
            String msg = "could not check for duplicates ";
            LOGGER.warning(msg + SOUVENIR_PATH);
            throw new SouvenirNotFoundException(msg);
        }
        return false;
    }
}
