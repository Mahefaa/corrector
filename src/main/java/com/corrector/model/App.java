package com.corrector.model;

import java.util.List;

public record App(String appName, List<DeployedEnv> environments) {}
