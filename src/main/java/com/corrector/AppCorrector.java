package com.corrector;

import com.corrector.model.App;
import com.corrector.model.evaluated.CorrectedApp;
import com.corrector.model.evaluated.CorrectedEnv;
import java.util.List;
import java.util.function.Function;

public class AppCorrector implements Function<App, CorrectedApp> {
  private final EnvironmentCorrector environmentCorrector;

  public AppCorrector() {
    this.environmentCorrector = new EnvironmentCorrector();
  }

  @Override
  public CorrectedApp apply(App app) {
    System.out.println("correcting " + app);
    List<CorrectedEnv> correctedEnvs =
        app.environments().stream().map(environmentCorrector).toList();
    return new CorrectedApp(app.appName(), correctedEnvs);
  }
}
