package com.corrector.model;

import java.net.URI;

public record DeployedEnv(EnvType envType, URI uri) {
  public enum EnvType {
    PROD,
    PREPROD;
  }

  public URI helloUri() {
    return uri.resolve("hello");
  }
}
