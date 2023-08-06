package com.storage.dao;

import com.storage.model.entity.Brand;
import com.storage.model.entity.Souvenir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public
class SouvenirFileStorageTest {

    public final static String SOUVENIR_PATH = "src/test/resources/souvenirs.csv";
    @Mock
    private SouvenirFileStorage mockStorage;
    private final List<Souvenir> souvenirsBefore =
            new SouvenirFileStorage().readFromCsv(SOUVENIR_PATH);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/souvenirs.csv", numLinesToSkip = 1)
    @DisplayName("Given valid souvenir data, delete method should delete and return the souvenir")
    void testDeleteFromCsv(
            String brandName, String brandCountry, LocalDateTime issue, String souvenirName, Double price) {
        Souvenir souvenir = Souvenir
                .builder()
                .brand(Brand
                        .builder()
                        .name(brandName)
                        .country(brandCountry)
                        .build())
                .issue(issue)
                .name(souvenirName)
                .price(price)
                .build();

        when(mockStorage.readFromCsv(SOUVENIR_PATH)).thenReturn(souvenirsBefore);
        List<Souvenir> before = mockStorage.readFromCsv(SOUVENIR_PATH);
        int countBefore = before.size();
        Optional<Souvenir> result = Optional.of(souvenir);
        Iterator<Souvenir> iterator = before.iterator();
        while (iterator.hasNext()) {
            Souvenir s = iterator.next();
            if (s.equals(result.get())) {
                iterator.remove();
            }
        }
        int countAfter = before.size();
        assertTrue(result.isPresent());
        assertEquals(souvenirName,
                result.get()
                      .getName());
        assertEquals(countBefore, countAfter);
    }
}
