package com.corrector;

import com.corrector.dataproviders.CsvDataProvider;
import com.corrector.dataproviders.DbDataProvider;
import com.corrector.fileLoader.ClasspathResourceLoader;
import com.corrector.model.corrected.CorrectedApp;
import com.corrector.scoreWriter.ScoreWriter;
import java.util.List;
import java.util.Map;

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
    ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
    var dbDataProvider = new DbDataProvider(resourceLoader);
    var csvDataProvider = new CsvDataProvider(resourceLoader);
    var deployedApps = dbDataProvider.apply("prod-jcloudify_apps_2025-02-13_14-53-29.json");
    var students = csvDataProvider.apply("answers_jo_2024_feb13_11_51.csv");

    Corrector corrector = new Corrector();
    Map<String, List<CorrectedApp>> correctedApps = corrector.computeScoreByStudent(deployedApps);

    ScoreWriter scoreWriter = new ScoreWriter();
    scoreWriter.writeScores(students, correctedApps);
  }
}
