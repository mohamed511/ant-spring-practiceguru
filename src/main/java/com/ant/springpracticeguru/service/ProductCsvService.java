package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.ProductCSVRecord;

import java.io.File;
import java.util.List;

public interface ProductCsvService {
    List<ProductCSVRecord> convertCSV(File csvFile);
}
