package com.corrector;

import com.corrector.model.App;
import com.corrector.model.corrected.CorrectedApp;
import com.corrector.model.corrected.CorrectedEnv;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

public class AppCorrector implements Function<App, CorrectedApp> {
  private final EnvironmentCorrector environmentCorrector;
  private static final Pattern regex = Pattern.compile("^hello-std.*");

  public AppCorrector() {
    this.environmentCorrector = new EnvironmentCorrector();
  }

  @Override
  public CorrectedApp apply(App app) {
    System.out.println("correction " + app.appName());
    if (!regex.matcher(app.appName()).matches()) {
      System.out.println(
          "Avertissement, nom d'application non-conforme au format attendu " + app.appName());
    }
    List<CorrectedEnv> correctedEnvs =
        app.environments().stream().map(environmentCorrector).toList();
    return new CorrectedApp(app.appName(), correctedEnvs);
  }
}
