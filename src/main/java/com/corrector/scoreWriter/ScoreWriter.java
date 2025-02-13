package com.corrector.scoreWriter;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

import com.corrector.model.corrected.CorrectedApp;
import com.corrector.model.csv.CsvUserRecord;
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
      List<CsvUserRecord> students, Map<String, List<CorrectedApp>> evaluatedStudents) {
    Map<String, List<CorrectedApp>> copy = new HashMap<>(evaluatedStudents);
    FileWriter sw = new FileWriter(Instant.now().toString() + "_answers.csv");
    CSVFormat csvFormat = DEFAULT.builder().setHeader("std", "preprod", "prod").build();
    students.forEach(
        student -> copy.putIfAbsent(student.stdLowercase(), List.of(CorrectedApp.empty())));
    try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
      copy.forEach(
          (std, evaluatedApps) -> {
            if (evaluatedApps.stream()
                .anyMatch(correctedApp -> correctedApp.isProdOk() && correctedApp.isPreprodOk())) {
              System.out.println("well done student " + std);
              newline(printer, std, 2, 2);
              return;
            } else if (evaluatedApps.stream()
                .anyMatch(
                    correctedApp -> correctedApp.isProdOk() && (!correctedApp.isPreprodOk()))) {
              newline(printer, std, 0, 2);
              return;
            } else if (evaluatedApps.stream()
                .anyMatch(
                    correctedApp -> (!correctedApp.isProdOk()) && correctedApp.isPreprodOk())) {
              newline(printer, std, 2, 0);
              return;
            } else {
              newline(printer, std, 0, 0);
              return;
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void newline(CSVPrinter printer, String std, int preprodScore, int prodScore) {
    System.out.println("std : " + std + " preprod? " + preprodScore + " prod? " + prodScore);
    try {
      printer.printRecord(std, preprodScore, prodScore);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
