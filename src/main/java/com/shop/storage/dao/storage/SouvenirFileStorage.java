package com.shop.storage.dao.storage;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.shop.storage.model.entity.Souvenir;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class SouvenirFileStorage
        implements FileStorage<Souvenir> {

    private static final String SOUVENIR_PATH = "souvenir.csv";
    private static final String HEADER_CSV = "BRAND,COUNTRY,ISSUE,NAME,PRICE";
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
            log.error(e.getMessage());
        }
        return souvenirs;
    }

    @Override
    public Optional<Souvenir> update(Souvenir souvenir) {
        Optional<Souvenir> result = Optional.empty();
        List<Souvenir> souvenirs = readFromCsv();
        for (int i = 0; i < souvenirs.size(); i++) {
            if (souvenirs.get(i).getName().equals(souvenir.getName())) {
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
            if (souvenir.getName().equals(name)) {
                iterator.remove();
                result = Optional.of(souvenir);
            }
        }
        writeToCsvFile(souvenirs);
        return result;
    }

    private void writeToCsvFile(List<Souvenir> souvenirs) {
        try (ICSVWriter csvWriter = new CSVWriterBuilder(
                new FileWriter(SOUVENIR_PATH))
                .withQuoteChar(NO_QUOTE_CHARACTER).build()) {
            csvWriter.writeNext(FIELDS);
            souvenirs.stream()
                     .map(this::getData)
                     .forEach(csvWriter::writeNext);
            log.info("CSV was file updated");
        } catch (IOException e) {
            log.error("Error occurred while updating the CSV file: {}", SOUVENIR_PATH);
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
                souvenir.getPrice().toString()
        };
    }
}
