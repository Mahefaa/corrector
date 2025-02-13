package com.corrector.model.db;

import com.corrector.model.DeployedEnv;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.time.Instant;

public record DatabaseDeployedAppRecord(
    @JsonProperty("app_id") String appId,
    @JsonProperty("id_user") String idUser,
    @JsonProperty("app_name") String appName,
    @JsonProperty("std") String std,
    @JsonProperty("environment_type") DeployedEnv.EnvType environmentType,
    @JsonProperty("deployed_url") URI deployedUrl,
    @JsonProperty("deployed_at") Instant deployedAt) {}
