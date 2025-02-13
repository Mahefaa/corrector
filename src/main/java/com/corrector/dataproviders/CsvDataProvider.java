package com.corrector.dataproviders;

import com.corrector.fileLoader.ClasspathResourceLoader;
import com.corrector.model.csv.CsvUserRecord;
import com.corrector.model.csv.reader.CsvUserRecordReader;
import java.util.List;

public class CsvDataProvider implements DataProvider<List<CsvUserRecord>> {
  private final CsvUserRecordReader reader;
  private final ClasspathResourceLoader resourceLoader;

  public CsvDataProvider(ClasspathResourceLoader resourceLoader) {
    this.reader = new CsvUserRecordReader();
    this.resourceLoader = resourceLoader;
  }

  @Override
  public List<CsvUserRecord> apply(String s) {
    System.out.println("chargement des données CSV");
    var read = reader.apply(resourceLoader.apply(s));
    System.out.println(read.size() + " données chargées");
    return read;
  }
}
