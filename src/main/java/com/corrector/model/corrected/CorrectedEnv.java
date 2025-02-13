package com.corrector.model.corrected;

import static com.corrector.model.corrected.CorrectedEnv.EnvEvaluationResult.OK;

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
