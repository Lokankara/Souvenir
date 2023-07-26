package com.storage.dao.storage;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.storage.model.entity.Souvenir;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@RequiredArgsConstructor
public class SouvenirFileStorage
        implements FileStorage<Souvenir> {

    private static final Logger LOGGER = Logger.getLogger(SouvenirFileStorage.class.getName());
    private static final String SOUVENIR_PATH = "src/main/resources/data/souvenir.csv";
    private static final String HEADER_CSV = "BRAND,COUNTRY,ISSUE,NAME,PRICE";
    private static final String[] FIELDS = {"brand", "country", "issue", "name", "price"};

    @Override
    public Optional<Souvenir> saveToCsv(Souvenir souvenir) {
        try (FileWriter writer = new FileWriter(SOUVENIR_PATH, true)) {
            if (isWriteHeader(SOUVENIR_PATH, HEADER_CSV)) {
                writer.write(HEADER_CSV + "\n");
            }
            if (!isDuplicate(souvenir)) {
                writer.write(getLine(souvenir));
                return Optional.of(souvenir);
            } else {
                LOGGER.info("Duplicate entry, not saved.");
                return Optional.empty();
            }
        } catch (IOException e) {
            LOGGER.warning("could not save Souvenir From Csv");
            return Optional.empty();
        }
    }

    @SneakyThrows
    @Override
    public List<Souvenir> readFromCsv() {
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
            LOGGER.warning(e.getMessage());
        }
        return souvenirs;
    }

    @Override
    public Optional<Souvenir> update(Souvenir souvenir) {
        Optional<Souvenir> result = Optional.empty();
        List<Souvenir> souvenirs = readFromCsv();
        for (int i = 0; i < souvenirs.size(); i++) {
            if (souvenirs.get(i)
                         .getName()
                         .equals(souvenir.getName())) {
                souvenirs.set(i, souvenir);
                result = Optional.of(souvenir);
                break;
            }
        }
        writeToCsvFile(souvenirs);
        return result;
    }

    @Override
    public Optional<Souvenir> deleteFromCsv(String name) {
        Optional<Souvenir> result = Optional.empty();
        List<Souvenir> souvenirs = readFromCsv();
        Iterator<Souvenir> iterator = souvenirs.iterator();
        while (iterator.hasNext()) {
            Souvenir souvenir = iterator.next();
            if (souvenir.getName()
                        .equals(name)) {
                iterator.remove();
                result = Optional.of(souvenir);
            }
        }
        writeToCsvFile(souvenirs);
        return result;
    }

    private void writeToCsvFile(List<Souvenir> souvenirs) {
        try (ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(
                SOUVENIR_PATH)).withQuoteChar(NO_QUOTE_CHARACTER)
                               .build()) {
            csvWriter.writeNext(FIELDS);
            souvenirs.stream()
                     .map(this::getData)
                     .forEach(csvWriter::writeNext);
            LOGGER.info("CSV was file updated");
        } catch (IOException e) {
            LOGGER.warning("Error occurred while updating the CSV file: {}" + SOUVENIR_PATH);
        }
    }

    private String getLine(Souvenir souvenir) {
        return String.join(",",
                getData(souvenir)) + "\n";
    }

    private String[] getData(Souvenir souvenir) {
        return new String[]{
                souvenir.getBrand().getName(),
                souvenir.getBrand().getCountry(),
                souvenir.getIssue().toString(),
                souvenir.getName(),
                souvenir.getPrice().toString()};
    }

    private boolean isDuplicate(Souvenir souvenir) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SOUVENIR_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(souvenir.getName())
                        && values[1].equals(souvenir.getBrand()
                                                    .getCountry())
                        && values[2].equals(souvenir.getBrand()
                                                    .getCountry())) {
                    return true;
                }
            }
        } catch (IOException e) {
            LOGGER.warning("could not check for duplicates");
        }
        return false;
    }
}
