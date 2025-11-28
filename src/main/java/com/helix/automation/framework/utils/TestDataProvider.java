package com.helix.automation.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading test data from JSON and CSV files.
 * Supports data-driven testing with TestNG DataProvider.
 */
public class TestDataProvider {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Read JSON file from classpath and convert to specified type
     * 
     * @param resourcePath Path to JSON file in resources folder
     * @param valueType    Class type to deserialize into
     * @return Deserialized object
     */
    public static <T> T readJson(String resourcePath, Class<T> valueType) {
        try (InputStream is = TestDataProvider.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new RuntimeException("Resource not found: " + resourcePath);
            }
            return objectMapper.readValue(is, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from: " + resourcePath, e);
        }
    }

    /**
     * Read CSV file from classpath and return as list of string arrays
     * 
     * @param resourcePath Path to CSV file in resources folder
     * @return List of string arrays, each representing a row
     */
    public static List<String[]> readCsv(String resourcePath) {
        try (InputStream is = TestDataProvider.class.getClassLoader().getResourceAsStream(resourcePath);
                CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            return reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Failed to read CSV from: " + resourcePath, e);
        }
    }

    /**
     * Read CSV file and convert to list of maps (header -> value)
     * 
     * @param resourcePath Path to CSV file in resources folder
     * @return List of maps where keys are column headers
     */
    public static List<Map<String, String>> readCsvAsMap(String resourcePath) {
        List<String[]> rows = readCsv(resourcePath);
        if (rows.isEmpty()) {
            return new ArrayList<>();
        }

        String[] headers = rows.get(0);
        List<Map<String, String>> result = new ArrayList<>();

        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            Map<String, String> rowMap = new HashMap<>();
            for (int j = 0; j < headers.length && j < row.length; j++) {
                rowMap.put(headers[j], row[j]);
            }
            result.add(rowMap);
        }

        return result;
    }

    /**
     * Convert list of objects to Object[][] for TestNG DataProvider
     * 
     * @param data List of test data objects
     * @return 2D array suitable for @DataProvider
     */
    public static Object[][] toDataProviderArray(List<?> data) {
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }

    /**
     * Convert list of maps to Object[][] for TestNG DataProvider
     * Each map becomes a single row parameter
     * 
     * @param data List of maps
     * @return 2D array suitable for @DataProvider
     */
    public static Object[][] mapListToDataProvider(List<Map<String, String>> data) {
        return toDataProviderArray(data);
    }
}
