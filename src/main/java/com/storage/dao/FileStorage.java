package com.storage.dao;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;

public interface FileStorage<T> {

    Logger LOGGER = Logger.getLogger(FileStorage.class.getName());

    T saveToCsv(T t);

    List<T> readFromCsv(String path);

    T updateCsv(T t);

    void deleteFromCsv(String name);

    String[] getData(T t);

    default boolean isWriteHeader(
            String filePath,
            String header) {
        boolean writeHeader = true;
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(file))) {
                if (header.equalsIgnoreCase(reader.readLine())) {
                    writeHeader = false;
                }
            } catch (IOException e) {
                LOGGER.warning("Could not read csv file: " + filePath);
            }
        }
        return writeHeader;
    }

    default void writeToCsvFile(List<T> t, String path, String[] fields) {
        try (ICSVWriter csvWriter = new CSVWriterBuilder(
                new FileWriter(path))
                .withQuoteChar(NO_QUOTE_CHARACTER)
                .build()) {
            csvWriter.writeNext(fields);
            t.stream()
                     .map(this::getData)
                     .forEach(csvWriter::writeNext);
            LOGGER.info("CSV was file updated");
        } catch (IOException e) {
            LOGGER.warning("Cannot updating the CSV file: {}"
                    + path);
        }
    }
}
