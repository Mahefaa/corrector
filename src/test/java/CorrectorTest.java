import static org.junit.jupiter.api.Assertions.assertEquals;

import com.corrector.Corrector;
import com.corrector.dataproviders.CsvDataProvider;
import com.corrector.dataproviders.DbDataProvider;
import com.corrector.fileLoader.ClasspathResourceLoader;
import com.corrector.model.corrected.CorrectedApp;
import com.corrector.scoreWriter.ScoreWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CorrectorTest {
  @Test
  void correct_std210_ok() throws IOException {
    ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
    var dbDataProvider = new DbDataProvider(resourceLoader);
    var csvDataProvider = new CsvDataProvider(resourceLoader);
    var deployedApps = dbDataProvider.apply("mock.json");
    var students = csvDataProvider.apply("mock.csv");

    Corrector corrector = new Corrector();
    Map<String, List<CorrectedApp>> correctedApps = corrector.computeScoreByStudent(deployedApps);

    ScoreWriter scoreWriter = new ScoreWriter();
    var result = scoreWriter.writeScores(students, correctedApps);

    assertEquals(readFile(resourceLoader.apply("expected.csv")), readFile(result));
  }

  private static String readFile(File file) throws IOException {
    return new String(Files.readAllBytes(file.toPath()));
  }
}
