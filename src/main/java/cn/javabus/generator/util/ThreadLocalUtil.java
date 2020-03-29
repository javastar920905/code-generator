package cn.javabus.generator.util;

import cn.javabus.generator.model.DatabaseConfig;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ou.zhenxing on 2020-03-28.
 */
public class ThreadLocalUtil {
    private static Map<String,DatabaseConfig> dbConfigsMap=new HashMap<>();
    public static ThreadLocal<DatabaseConfig> selectedDatabaseConfig = new ThreadLocal<>();

    public static DatabaseConfig getDatabaseConfigs(String dbConfigName) {
        if (CollectionUtils.isEmpty(dbConfigsMap)) {
            synchronized (dbConfigsMap) {
                if (CollectionUtils.isEmpty(dbConfigsMap)) {
                    forceLoadDatabaseConfigs();
                }
            }
        }
        if (CollectionUtils.isEmpty(dbConfigsMap)){
            return null;
        }
        return dbConfigsMap.get(dbConfigName);
    }

    public static void forceLoadDatabaseConfigs() {
        try {
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            //使用map存儲 提高查詢效率
            dbConfigs.forEach(config->dbConfigsMap.put(config.getName(),config ));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
