package cn.javabus.generator.util;

import cn.javabus.generator.model.DatabaseConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ou.zhenxing on 2020-03-28.
 */
public class ThreadLocalUtil {
    private static List<DatabaseConfig> dbConfigs=new ArrayList<>(0);
    public static ThreadLocal<DatabaseConfig> selectedDatabaseConfig = new ThreadLocal<>();

    public static List<DatabaseConfig> getDatabaseConfigs() {
        if (dbConfigs == null) {
            synchronized (dbConfigs) {
                if (dbConfigs == null) {
                    forceLoadDatabaseConfigs();
                }
            }
        }
        return dbConfigs;
    }

    public static void forceLoadDatabaseConfigs() {
        try {
            dbConfigs = ConfigHelper.loadDatabaseConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
