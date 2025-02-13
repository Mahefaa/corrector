package com.corrector.dataproviders;

import com.corrector.fileLoader.ClasspathResourceLoader;
import com.corrector.model.db.DatabaseDeployedAppRecord;
import com.corrector.model.db.reader.DatabaseDeployedAppRecordReader;
import java.util.List;

public class DbDataProvider implements DataProvider<List<DatabaseDeployedAppRecord>> {
  private final DatabaseDeployedAppRecordReader reader;
  private final ClasspathResourceLoader resourceLoader;

  public DbDataProvider(ClasspathResourceLoader resourceLoader) {
    this.reader = new DatabaseDeployedAppRecordReader();
    this.resourceLoader = resourceLoader;
  }

  @Override
  public List<DatabaseDeployedAppRecord> apply(String s) {
    System.out.println("chargement des données de la base : " + s);
    var read = reader.apply(resourceLoader.apply(s));
    System.out.println(read.size() + " données chargées");
    return read;
  }
}
