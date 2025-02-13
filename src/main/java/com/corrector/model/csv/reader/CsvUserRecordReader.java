package com.corrector.model.csv.reader;

import com.corrector.model.csv.CsvUserRecord;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvUserRecordReader implements Function<File, List<CsvUserRecord>> {
  @Override
  public List<CsvUserRecord> apply(File file) {
    var users = new ArrayList<CsvUserRecord>();
    try (CSVParser csvParser =
        new CSVParser(
            Files.newBufferedReader(file.toPath()), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
      for (CSVRecord record : csvParser) {
        var user = new CsvUserRecord(record.get("Référence étudiante"));
        users.add(user);
      }
      return users;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
