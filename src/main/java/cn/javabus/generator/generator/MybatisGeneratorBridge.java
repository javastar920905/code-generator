package cn.javabus.generator.generator;

import cn.javabus.generator.model.DatabaseConfig;
import cn.javabus.generator.model.GeneratorConfig;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.ColumnOverride;
import org.mybatis.generator.config.IgnoredColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The bridge between GUI and the mybatis generator. All the operation to  mybatis generator should proceed through this
 * class
 * <p>
 * Created by Owen on 6/30/16.
 * update by javabus.cn on 2019-05-22
 */
public abstract class MybatisGeneratorBridge {

    protected static final Logger _LOG = LoggerFactory.getLogger(MybatisGeneratorBridge.class);

    /**
     * 保存了项目配置 点击进去可以查看示例值
     **/
    protected GeneratorConfig generatorConfig;

    protected DatabaseConfig selectedDatabaseConfig;

    protected ProgressCallback progressCallback;

    protected List<IgnoredColumn> ignoredColumns;

    protected List<ColumnOverride> columnOverrides;


    public MybatisGeneratorBridge(GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
        this.selectedDatabaseConfig = generatorConfig.getSelectedDatabaseConfig();
    }

    /**
     * 根据配置生成代码
     *
     * @throws Exception
     */
    public abstract void generate() throws Exception;


    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public void setIgnoredColumns(List<IgnoredColumn> ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
        this.columnOverrides = columnOverrides;
    }
}
