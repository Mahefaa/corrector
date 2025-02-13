package com.corrector;

import static com.corrector.model.corrected.CorrectedEnv.EnvEvaluationResult.KO;
import static com.corrector.model.corrected.CorrectedEnv.EnvEvaluationResult.OK;

import com.corrector.model.DeployedEnv;
import com.corrector.model.corrected.CorrectedEnv;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Function;

public class EnvironmentCorrector implements Function<DeployedEnv, CorrectedEnv> {
  private final HttpClient httpClient = HttpClient.newBuilder().build();

  @Override
  public CorrectedEnv apply(DeployedEnv env) {
    try {
      URI uri = env.helloUri();
      System.out.println("correction de " + env.envType() + " uri : " + uri);
      var response =
          httpClient.send(HttpRequest.newBuilder().GET().uri(uri).build(), BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        System.out.println("environnement " + env.envType() + " " + env.helloUri() + " OK");
        return new CorrectedEnv(env, OK);
      }
    } catch (IOException | InterruptedException e) {
      System.out.println("erreur lors de la lecture de l'endpoint, KO");
      System.out.println(e.getMessage());
    }

    System.out.println("environnement " + env.envType() + " " + env.helloUri() + " KO");
    return new CorrectedEnv(env, KO);
  }
}
