package com.corrector.model.corrected;

import static com.corrector.model.DeployedEnv.EnvType.PREPROD;
import static com.corrector.model.DeployedEnv.EnvType.PROD;

import java.util.ArrayList;
import java.util.List;

public record CorrectedApp(String id, List<CorrectedEnv> deployedEnvs) {
  public boolean isProdOk() {
    return deployedEnvs.stream()
        .filter(env -> PROD.equals(env.deployedEnv().envType()))
        .anyMatch(CorrectedEnv::isOk);
  }

  public boolean isPreprodOk() {
    return deployedEnvs.stream()
        .filter(env -> PREPROD.equals(env.deployedEnv().envType()))
        .anyMatch(CorrectedEnv::isOk);
  }

  public static CorrectedApp empty() {
    return new CorrectedApp("", new ArrayList<>());
  }
}
