package com.shop.storage.dao.io;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ObjectStream<T> {

    public void write(
            List<T> values,
            String outputFile) {

        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(outputFile))) {

            output.writeObject(values);

            log.info("File  was written" + outputFile);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public List<T> read(String outputFile) {
        List<T> values = new ArrayList<>();

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(outputFile))) {

            values = (List<T>) input.readObject();

            log.info("File was read" + outputFile);
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return values;
    }
}
