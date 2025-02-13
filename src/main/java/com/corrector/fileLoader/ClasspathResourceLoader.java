package com.corrector.fileLoader;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.function.Function;

public class ClasspathResourceLoader implements Function<String, File> {
  @Override
  public File apply(String pathFromClassPath) {
    return new File(
        requireNonNull(this.getClass().getClassLoader().getResource(pathFromClassPath)).getFile());
  }
}
