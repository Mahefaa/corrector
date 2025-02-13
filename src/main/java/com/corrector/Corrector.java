package com.corrector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;

import com.corrector.model.App;
import com.corrector.model.DeployedEnv;
import com.corrector.model.corrected.CorrectedApp;
import com.corrector.model.db.DatabaseDeployedAppRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Corrector {
  private final AppCorrector appCorrector;

  public Corrector() {
    this.appCorrector = new AppCorrector();
  }

  public Map<String, List<CorrectedApp>> computeScoreByStudent(
      List<DatabaseDeployedAppRecord> dbData) {
    Map<String, List<CorrectedApp>> res = new HashMap<>();
    Map<String, App> grouped =
        dbData.stream()
            .collect(
                groupingBy(
                    DatabaseDeployedAppRecord::std,
                    collectingAndThen(
                        Collectors.toList(),
                        deployedApps ->
                            new App(
                                deployedApps.getFirst().appName(),
                                deployedApps.stream()
                                    .map(a -> new DeployedEnv(a.environmentType(), a.deployedUrl()))
                                    .toList()))));
    grouped.forEach(
        (std, app) -> {
          System.out.println("--------- BEGIN correcting ----------" + std);
          var corrected = evaluateApp(app);
          if (res.containsKey(std)) {
            System.out.println(std + " has 1 more app " + corrected.name());
            res.get(std).add(corrected);
          } else {
            var array = new ArrayList<CorrectedApp>();
            array.add(corrected);
            res.put(std, array);
          }
          System.out.println("--------- END correcting ----------" + std);
        });

    return res;
  }

  public CorrectedApp evaluateApp(App app) {
    return appCorrector.apply(app);
  }
}
