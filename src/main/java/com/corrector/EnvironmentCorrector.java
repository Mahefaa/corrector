package com.corrector;

import static com.corrector.model.corrected.CorrectedEnv.EnvEvaluationResult.KO;
import static com.corrector.model.corrected.CorrectedEnv.EnvEvaluationResult.OK;

import com.corrector.model.DeployedEnv;
import com.corrector.model.corrected.CorrectedEnv;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;

public class EnvironmentCorrector implements Function<DeployedEnv, CorrectedEnv> {
  private final HttpClient httpClient = HttpClient.newBuilder().build();

  @Override
  public CorrectedEnv apply(DeployedEnv env) {
    try {
      System.out.println("correcting " + env);
      URI uri = env.helloUri();
      var response =
          httpClient.send(
              HttpRequest.newBuilder().GET().uri(uri).build(),
              HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        CorrectedEnv correctedEnv = new CorrectedEnv(env, OK);
        System.out.println(correctedEnv);
        return correctedEnv;
      }
    } catch (IOException | InterruptedException e) {
      System.out.println(e.getMessage());
    }

    CorrectedEnv correctedEnv = new CorrectedEnv(env, KO);
    System.out.println(correctedEnv);
    return correctedEnv;
  }
}
