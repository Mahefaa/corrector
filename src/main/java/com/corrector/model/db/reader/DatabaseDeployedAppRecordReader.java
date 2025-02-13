package com.corrector.model.db.reader;

import com.corrector.model.db.DatabaseDeployedAppRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import lombok.SneakyThrows;

public class DatabaseDeployedAppRecordReader
    implements Function<File, List<DatabaseDeployedAppRecord>> {
  private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

  @SneakyThrows
  @Override
  public List<DatabaseDeployedAppRecord> apply(File file) {
    return objectMapper.readValue(file, new TypeReference<>() {});
  }
}
