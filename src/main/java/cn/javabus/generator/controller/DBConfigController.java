package cn.javabus.generator.controller;

import cn.javabus.generator.model.DatabaseConfig;
import cn.javabus.generator.util.ConfigHelper;
import cn.javabus.generator.util.DbUtil;
import cn.javabus.generator.util.Result;
import cn.javabus.generator.util.ThreadLocalUtil;
import com.jcraft.jsch.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.concurrent.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.EOFException;
import java.util.List;

/**
 * 数据库连接配置 管理
 *
 * @author ou.zhenxing on 2020-03-28.
 */
@Api(tags = "数据库连接配置 管理")
@RestController
@RequestMapping("/DBConfig")
public class DBConfigController {
    private static Logger logger = LoggerFactory.getLogger(DBConfigController.class);

    /**
     * 加载数据库连接列表 (前端自动取得当前数据库连接)
     *
     * @return
     */
    @GetMapping("getDBConfigList")
    @ApiOperation(" 加载数据库连接列表 (前端自动取得当前数据库连接)")
    public Result getDBConfigList() {
        try {
            List<DatabaseConfig> dbConfigs = ConfigHelper.loadDatabaseConfig();
            return Result.ok(dbConfigs);
        } catch (Exception e) {
            logger.error("connect db failed, reason: {}", e);
            return Result.fail(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }

    private DatabaseConfig getDBConfigByName(String dBConfigName) {
        DatabaseConfig selectedDatabaseConfig = null;
        List<DatabaseConfig> databaseConfigs = ThreadLocalUtil.getDatabaseConfigs();

        for (DatabaseConfig db : databaseConfigs) {
            if (db.getName().equals(dBConfigName)) {
                selectedDatabaseConfig = db;
                ThreadLocalUtil.selectedDatabaseConfig.set(db);
                break;
            }
        }
        return selectedDatabaseConfig;
    }

    /**
     * 根据选中数据库配置,创建数据库连接,加载数据库表列表
     *
     * @return
     */
    @ApiOperation("根据选中数据库配置,创建数据库连接,加载数据库表列表")
    @GetMapping("getTablesByDBConfig")
    public Result getTablesByDBConfig(@RequestParam String dBConfigName) {
        try {
            DatabaseConfig dbConfigByName = getDBConfigByName(dBConfigName);
            if (dbConfigByName == null) {
                return Result.fail("数据库配置名不存在" + dbConfigByName);
            }

            List<String> tableNames = DbUtil.getTableNames(dbConfigByName);
            return Result.ok(tableNames);
        } catch (Exception e) {
            logger.error("connect db failed, reason: {}", e);
            return Result.fail(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }


    @PostMapping("testDBConfig")
    public Result testConnection(@RequestBody DatabaseConfig config) {
        if (config == null) {
            return Result.fail("请求参数不能为空");
        }
        if (StringUtils.isAnyEmpty(config.getName(),
                config.getHost(),
                config.getPort(),
                config.getUsername(),
                config.getEncoding(),
                config.getDbType(),
                config.getSchema())) {
            return Result.fail("密码以外其他字段必填");
        }
        if (!config.getOverssh()) {//TCP/IP 连接
            try {
                DbUtil.getConnection(config);
                return Result.ok("连接成功");
            } catch (RuntimeException e) {
                logger.error("", e);
                Result.fail("连接失败, " + e.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                Result.fail("连接失败");
            }
        } else {//ssh 方式连接
            Session sshSession = DbUtil.getSSHSession(config);
            //如果不用异步，则视图会等方法返回才会显示
            Task task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    DbUtil.engagePortForwarding(sshSession, config);
                    DbUtil.getConnection(config);
                    return null;
                }
            };
            task.setOnFailed(event -> {
                Throwable e = task.getException();
                logger.error("task Failed", e);
                if (e instanceof RuntimeException) {
                    if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                        Result.fail(config.getLport() + "已经被占用，请换其他端口");
                    }
                    //端口转发一定不成功，导致数据库连接不上
                    Result.fail("连接失败:" + e.getMessage());
                    return;
                }

                if (e.getCause() instanceof EOFException) {
                    Result.fail("连接失败, 请检查数据库的主机名，并且检查端口和目标端口是否一致");
                    //端口转发已经成功，但是数据库连接不上，故需要释放连接
                    DbUtil.shutdownPortForwarding(sshSession);
                    return;
                }
                Result.fail("连接失败:" + e.getMessage());
                //可能是端口转发已经成功，但是数据库连接不上，故需要释放连接
                DbUtil.shutdownPortForwarding(sshSession);
            });
            task.setOnSucceeded(event -> {
                try {
                    Result.ok("连接成功");
                    DbUtil.shutdownPortForwarding(sshSession);
                } catch (Exception e) {
                    logger.error("", e);
                }
            });
            new Thread(task).start();
            //todo  需要等待线程返回结果
        }
        return Result.fail("");
    }

    @PostMapping("saveDBConfig")
    public Result saveDBConfig(@RequestBody DatabaseConfig databaseConfig) {
        if (StringUtils.isAnyEmpty(
                databaseConfig.getName(),
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getUsername(),
                databaseConfig.getEncoding(),
                databaseConfig.getDbType(),
                databaseConfig.getSchema())) {
            return Result.fail("密码以外其他字段必填");
        }


        try {
            ConfigHelper.saveDatabaseConfig(databaseConfig.getUpdate(), databaseConfig.getId(), databaseConfig);
            return Result.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("saveSSHDBConfig")
    public Result saveSSHConnection(@RequestBody DatabaseConfig config) {
        if (config == null) {
            return Result.fail("config 不能为空");
        }
        try {
            ConfigHelper.saveDatabaseConfig(config.getUpdate(), config.getId(), config);
            return Result.ok("可以加载数据库连接了");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }


    @PostMapping("delDBConfig")
    public Result delDBConfig(@RequestParam String dbConfigName) {
        try {
            DatabaseConfig dbConfigByName = getDBConfigByName(dbConfigName);
            if (dbConfigByName == null) {
                return Result.fail("数据库配置名不存在" + dbConfigByName);
            }
            ConfigHelper.deleteDatabaseConfig(dbConfigByName);
            return Result.ok();
        } catch (Exception e) {
            logger.error("Delete connection failed! Reason: " + e.getMessage());
            return Result.fail(e.getMessage() + "\n" + ExceptionUtils.getStackTrace(e));
        }
    }


}
