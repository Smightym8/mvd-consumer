package at.fhv.edc.demo;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.edc.spi.monitor.Monitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDataSourceAdapterImpl implements CsvDataSourceAdapter {
    private final String csvFilePath;
    private final Monitor monitor;

    public CsvDataSourceAdapterImpl(String csvFilePath, Monitor monitor) {
        this.csvFilePath = csvFilePath;
        this.monitor = monitor;
    }

    @Override
    public List<Map<String, String>> readData() {
        List<Map<String, String>> recordsList = new ArrayList<>();

        try (CSVParser csvParser = CSVParser.parse(new File(csvFilePath), StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> recordMap = new HashMap<>(csvRecord.toMap());
                recordsList.add(recordMap);
            }
        } catch (IOException e) {
            monitor.severe("Error reading CSV file " + csvFilePath);
        }

        return recordsList;
    }
}
