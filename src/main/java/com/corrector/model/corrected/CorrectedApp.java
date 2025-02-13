package com.corrector.model.corrected;

import static com.corrector.model.DeployedEnv.EnvType.PREPROD;
import static com.corrector.model.DeployedEnv.EnvType.PROD;

import java.util.ArrayList;
import java.util.List;

public record CorrectedApp(String name, List<CorrectedEnv> correctedEnvs) {
  public boolean isProdOk() {
    var envs =
        correctedEnvs.stream().filter(env -> PROD.equals(env.deployedEnv().envType())).toList();
    return envs.stream().anyMatch(CorrectedEnv::isOk);
  }

  public boolean isPreprodOk() {
    var envs =
        correctedEnvs.stream().filter(env -> PREPROD.equals(env.deployedEnv().envType())).toList();
    return envs.stream().anyMatch(CorrectedEnv::isOk);
  }

  public static CorrectedApp empty() {
    return new CorrectedApp("", new ArrayList<>());
  }
}
