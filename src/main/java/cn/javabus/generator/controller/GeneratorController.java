package cn.javabus.generator.controller;

import cn.javabus.generator.bridge.MybatisGeneratorBridge;
import cn.javabus.generator.model.DatabaseConfig;
import cn.javabus.generator.model.GeneratorConfig;
import cn.javabus.generator.util.ConfigHelper;
import cn.javabus.generator.util.DbUtil;
import cn.javabus.generator.util.Result;
import cn.javabus.generator.util.ThreadLocalUtil;
import com.jcraft.jsch.Session;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ou.zhenxing on 2020-03-28.
 */
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
    @PostMapping("generateCode")
    public Result generateCode(GeneratorConfig config) {
        if (config == null || StringUtils.isEmpty(config.getTableName())) {
            return Result.fail("请先在左侧选择数据库表");
        }
        String result = validateConfig(config);
        if (result != null) {
            return Result.fail(result);
        }
        if (!checkDirs(config)) {
            return Result.fail("选中目标目录检查,创建失败");
        }
        DatabaseConfig selectedDatabaseConfig= ThreadLocalUtil.selectedDatabaseConfig.get();

        MybatisGeneratorBridge bridge = new MybatisGeneratorBridge();
        bridge.setGeneratorConfig(config);
        bridge.setDatabaseConfig(selectedDatabaseConfig);
        bridge.setIgnoredColumns(ignoredColumns);
        bridge.setColumnOverrides(columnOverrides);
        try {
            //Engage PortForwarding
            Session sshSession = DbUtil.getSSHSession(selectedDatabaseConfig);
            DbUtil.engagePortForwarding(sshSession, selectedDatabaseConfig);

            bridge.generate();

            return Result.ok("代码生成成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 保存当前代码生成配置信息 命名建议 公司_项目_模块
     */
    @PostMapping("saveConfig")
    public Result saveGeneratorConfig(GeneratorConfig generatorConfig) {
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
     * @param generatorConfig
     * @return
     */
    @GetMapping("getConfigList")
    public Result getGeneratorConfig(GeneratorConfig generatorConfig) {
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
    public Result getGeneratorConfig(@RequestParam String generatorConfigName) {
        try {
            GeneratorConfig generatorConfig = ConfigHelper.loadGeneratorConfig(generatorConfigName);

            return Result.ok(generatorConfig);
        } catch (Exception e) {
            return Result.fail("保存配置失败");
        }
    }


    @GetMapping("delConfig")
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

    private String validateConfig(GeneratorConfig config) {
        String projectFolder = config.getProjectFolder();
        if (StringUtils.isEmpty(projectFolder)) {
            return "项目目录不能为空";
        }
        if (StringUtils.isEmpty(config.getDomainObjectName())) {
            return "类名不能为空";
        }
        if (StringUtils.isAnyEmpty(config.getModelPackageTargetFolder(), config.getMappingXMLTargetFolder(), config.getDaoTargetFolder())) {
            return "包名不能为空";
        }
        return null;
    }

    private boolean checkDirs(GeneratorConfig config) {
        List<String> dirs = new ArrayList<>();
        dirs.add(config.getProjectFolder());
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getModelPackageTargetFolder())));
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getDaoTargetFolder())));
        dirs.add(FilenameUtils.normalize(config.getProjectFolder().concat("/").concat(config.getMappingXMLTargetFolder())));
        boolean haveNotExistFolder = false;
        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.exists()) {
                haveNotExistFolder = true;
            }
        }
        if (haveNotExistFolder) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(FOLDER_NO_EXIST);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent()) {
                if (ButtonType.OK == optional.get()) {
                    try {
                        for (String dir : dirs) {
                            FileUtils.forceMkdir(new File(dir));
                        }
                        return true;
                    } catch (Exception e) {
                        logger.error("创建目录失败，请检查目录是否是文件而非目录", e);
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
