package cn.javabus.generator.controller;

import cn.javabus.generator.generator.MybatisGeneratorBridge;
import cn.javabus.generator.generator.impl.MybatisNormalGenertor;
import cn.javabus.generator.model.DatabaseConfig;
import cn.javabus.generator.model.GeneratorConfig;
import cn.javabus.generator.util.ConfigCacheUtil;
import cn.javabus.generator.util.ConfigHelper;
import cn.javabus.generator.util.DbUtil;
import cn.javabus.generator.util.Result;
import com.jcraft.jsch.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ou.zhenxing on 2020-03-28.
 */
@Api(tags = "生成代码管理")
@RestController
@RequestMapping("generator")
public class GeneratorController {
    private static Logger logger = LoggerFactory.getLogger(GeneratorController.class);
    private List<IgnoredColumn> ignoredColumns = new ArrayList<>();

    private List<ColumnOverride> columnOverrides = new ArrayList<>();

    //检查并创建不存在的文件夹
    private static final String FOLDER_NO_EXIST = "部分目录不存在，是否创建";


    /**
     * 生成代码
     */
    @ApiOperation("生成代码")
    @PostMapping("generateCode")
    public Result generateCode(@RequestBody GeneratorConfig config) {
        if (config == null || StringUtils.isEmpty(config.getTableName())) {
            return Result.fail("请先在左侧选择数据库表");
        }

        String result = config.validateConfig();
        if (result != null) {
            return Result.fail(result);
        }

        DatabaseConfig selectedDatabaseConfig;
        if (config.getSelectedDatabaseConfig() != null) {
            selectedDatabaseConfig = config.getSelectedDatabaseConfig();
        } else {
            selectedDatabaseConfig = ConfigCacheUtil.selectedDatabaseConfig;
        }

        if (selectedDatabaseConfig == null) {
            return Result.fail("请先选中一个数据库连接,选中要生成代码的数据表");
        }

        //创建代码生成器
        MybatisGeneratorBridge bridge = new MybatisNormalGenertor(config);
        bridge.setIgnoredColumns(ignoredColumns);
        bridge.setColumnOverrides(columnOverrides);
        try {
            //Engage PortForwarding
            Session sshSession = DbUtil.getSSHSession(selectedDatabaseConfig);
            DbUtil.engagePortForwarding(sshSession, selectedDatabaseConfig);

            //执行代码生成任务
            bridge.generate();

            return Result.ok("代码生成成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 保存当前代码生成配置信息 命名建议 公司_项目_模块
     */
    @PostMapping("saveConfig")
    @ApiOperation("保存当前代码生成配置信息 命名建议 公司_项目_模块")
    public Result saveGeneratorConfig(@RequestBody GeneratorConfig generatorConfig) {
        if (generatorConfig == null) {
            return Result.fail("参数不能为空");
        }
        String name = generatorConfig.getName();
        if (StringUtils.isEmpty(name)) {
            return Result.fail("名称不能为空");
        }
        logger.info("user choose name: {}", name);
        try {
            generatorConfig.setName(name);
            ConfigHelper.deleteGeneratorConfig(name);
            ConfigHelper.saveGeneratorConfig(generatorConfig);
            return Result.ok("配置保存成功!");
        } catch (Exception e) {
            return Result.fail("保存配置失败");
        }
    }

    /**
     * 加载配置列表
     *
     * @return
     */
    @GetMapping("getConfigList")
    @ApiOperation("加载配置列表")
    public Result getGeneratorConfigList() {
        try {
            List<GeneratorConfig> configs = ConfigHelper.loadGeneratorConfigs();
            return Result.ok(configs);
        } catch (Exception e) {
            return Result.fail("保存配置失败");
        }
    }

    /**
     * 点击应用名称加载  生成配置
     *
     * @param generatorConfigName
     * @return
     */
    @GetMapping("getConfig")
    @ApiOperation("点击应用名称加载  生成配置")
    public Result getGeneratorConfig(@RequestParam String generatorConfigName) {
        try {
            GeneratorConfig generatorConfig = ConfigHelper.loadGeneratorConfig(generatorConfigName);

            return Result.ok(generatorConfig);
        } catch (Exception e) {
            return Result.fail("保存配置失败");
        }
    }


    @DeleteMapping("delConfig")
    public Result delGeneratorConfig(@RequestParam String generatorConfigName) {
        try {
            ConfigHelper.deleteGeneratorConfig(generatorConfigName);
            return Result.ok("配置保存成功!");
        } catch (Exception e) {
            return Result.fail("保存配置失败");
        }
    }

    //设置忽略的列名
    public void setIgnoredColumns() {
        List<IgnoredColumn> ignoredColumns = new ArrayList<>();
        List<ColumnOverride> columnOverrides = new ArrayList<>();
//            items.stream().forEach(item -> {
//                if (!item.getChecked()) {
//                    IgnoredColumn ignoredColumn = new IgnoredColumn(item.getColumnName());
//                    ignoredColumns.add(ignoredColumn);
//                } else if (item.getTypeHandle() != null || item.getJavaType() != null || item.getPropertyName() != null) { // unchecked and have typeHandler value
//                    ColumnOverride columnOverride = new ColumnOverride(item.getColumnName());
//                    columnOverride.setTypeHandler(item.getTypeHandle());
//                    columnOverride.setJavaProperty(item.getPropertyName());
//                    columnOverride.setJavaType(item.getJavaType());
//                    columnOverrides.add(columnOverride);
//                }
//            });
//        }
    }


}
