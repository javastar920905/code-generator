package cn.javabus.generator;

import cn.javabus.generator.model.DatabaseConfig;
import cn.javabus.generator.util.ConfigHelper;
import cn.javabus.generator.util.DbUtil;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ou.zhenxing on 2020-03-28.
 */
public class MainTest {
    // DBConfigController dbConfigController = new DBConfigController();


    /**
     * 数据库连接测试
     */
    @Test
    public void test() {
        //1 保存数据库连接
        DatabaseConfig config = new DatabaseConfig();
        config.setName("db_test" + Math.random());
        config.setDbType("MySQL");
        config.setHost("47.93.121.11");
        config.setPort("3306");
        config.setUsername("root");
        config.setPassword("testdb");
        config.setEncoding("utf8");
        config.setSchema("gen_test");

        try {
            ConfigHelper.saveDatabaseConfig(false, null, config);


            //2 加载数据库连接列表
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            assert dbConfigs != null && dbConfigs.size() > 0;
            // 3 打印数据结构
            System.out.println(JSON.toJSONString(dbConfigs));
            //Result tables = dbConfigController.getTablesByDBConfig(dbConfigs.get(0).getName());
            List<String> tableNames = DbUtil.getTableNames(dbConfigs.get(dbConfigs.size() - 1));

            System.out.println(JSON.toJSONString(tableNames));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void delAllDatabaseConfig() {
        try {
            //1 加载数据库连接列表
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            for (DatabaseConfig dbConfig : dbConfigs) {
                ConfigHelper.deleteDatabaseConfig(dbConfig);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
