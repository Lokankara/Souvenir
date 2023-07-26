//package com.shop.storage.dao.io;
//
//import com.opencsv.CSVWriter;
//import com.opencsv.bean.ColumnPositionMappingStrategy;
//import com.opencsv.bean.CsvToBeanBuilder;
//import com.shop.storage.dao.storage.FileStorage;
//import com.shop.storage.model.entity.Souvenir;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Service
//public class CsvProvider implements FileStorage<Souvenir> {
//
//