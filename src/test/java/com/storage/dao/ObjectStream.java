package com.storage.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectStream<T> {

    public void write(
            List<T> values,
            String outputFile) {

        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(outputFile))) {

            output.writeObject(values);

            System.out.println(("File  was written" + outputFile));
        } catch (IOException e) {
            System.err.println((e.getMessage()));
        }
    }

    public List<T> read(String outputFile) {
        List<T> values = new ArrayList<>();

        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(outputFile))) {

            values = (List<T>) input.readObject();

            System.out.println(("File was read" + outputFile));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println((e.getMessage()));
        }
        return values;
    }
}
