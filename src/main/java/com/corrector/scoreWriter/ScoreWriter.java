package com.corrector.scoreWriter;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

import com.corrector.model.csv.CsvUserRecord;
import com.corrector.model.evaluated.CorrectedApp;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ScoreWriter {

  public ScoreWriter() {}

  @SneakyThrows
  public void writeScores(
      List<CsvUserRecord> students, Map<String, CorrectedApp> evaluatedStudents) {
    Map<String, CorrectedApp> copy = new HashMap<>(evaluatedStudents);
    FileWriter sw = new FileWriter(Instant.now().toString() + "_answers.csv");
    CSVFormat csvFormat = DEFAULT.builder().setHeader("std, preprod, prod").build();
    try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
      students.forEach(student -> copy.putIfAbsent(student.std(), CorrectedApp.empty()));
      copy.forEach(
          (std, evaluatedApp) -> {
            int preprodScore = evaluatedApp.isPreprodOk() ? 2 : 0;
            int prodScore = evaluatedApp.isProdOk() ? 2 : 0;
            System.out.println(
                "std : " + std + " preprod? " + preprodScore + " prod? " + prodScore);
            newline(printer, std, preprodScore, prodScore);
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void newline(CSVPrinter printer, String std, int preprodScore, int prodScore) {
    try {
      printer.printRecord(std, preprodScore, prodScore);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
