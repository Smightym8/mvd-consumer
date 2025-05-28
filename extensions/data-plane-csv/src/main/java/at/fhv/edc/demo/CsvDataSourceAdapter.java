package at.fhv.edc.demo;

import java.util.List;
import java.util.Map;

public interface CsvDataSourceAdapter {
    List<Map<String, String>> readData();
}
