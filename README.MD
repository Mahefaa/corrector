# programme de correction des exercices des L3 sur jcloudify-api

## Datasources
### base de donnée de jcloudify
```postgresql
with distinct_deployments_by_user as (select distinct on (aed.deployed_url) a.id                                        as app_id,
                                                                            a.id_user,
                                                                            a.name                                      as app_name,
                                                                            REGEXP_REPLACE(a.name, '^.*(std\d+)', '\1') as std,
                                                                            e.environment_type,
                                                                            aed.deployed_url,
                                                                            ds.timestamp                                as deployed_at
                                      from application a
                                               inner join environment e on a.id = e.id_application
                                               inner join app_environment_deployment aed on e.id = aed.env_id
                                               inner join deployment_state ds on aed.id = ds.app_env_deployment_id
                                      where ds.progression_status = 'COMPUTE_STACK_DEPLOYED'
                                        and a.archived = false
                                        and a.name ilike '%std%'
                                        and aed.deployed_url is not null
                                      order by aed.deployed_url)
select app_id,
       id_user,
       app_name,
       std,
       environment_type,
       deployed_url,
       TO_CHAR(deployed_at, 'YYYY-MM-DD"T"HH24:MI:SS.MS"Z"') as deployed_at
from distinct_deployments_by_user
order by std;
```

### base de donnée sheets
https://docs.google.com/spreadsheets/d/1Bm47-qmkBWlFpHonkWg1aMfWUgiiX3BxWThyzBMYM3Q/edit?usp=sharing

### résultat: 
csv contenant les examens évalués.

### critères d'évaluation: 

GET urlPreprod/hello OK ? 2 pts : 0 pts
GET urlProd/hell OK ? 2 pts : 0pts