package com.corrector.model.evaluated;

import static com.corrector.model.evaluated.CorrectedEnv.EnvEvaluationResult.OK;

import com.corrector.model.DeployedEnv;

public record CorrectedEnv(DeployedEnv deployedEnv, EnvEvaluationResult envEvaluationResult) {
  public enum EnvEvaluationResult {
    OK,
    KO;
  }

  public boolean isOk() {
    return OK.equals(envEvaluationResult);
  }
}
