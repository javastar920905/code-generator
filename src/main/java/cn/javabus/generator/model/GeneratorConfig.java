package cn.javabus.generator.model;


import lombok.Getter;
import lombok.Setter;

/**
 * GeneratorConfig is the Config of mybatis generator config exclude database
 * config
 * <p>
 * Created by Owen on 6/16/16.
 * <p>
 * <p> 示例值
 * generatorConfig = {GeneratorConfig@4549}
 * name = null
 * connectorJarPath = null
 * projectFolder = "E:\gitRepository\spring-boot-api-project-seed\serviceA-provider"
 * modelPackage = "com.company.project.entity"
 * modelPackageTargetFolder = "src/main/java"
 * daoPackage = "com.company.project.dao"
 * daoTargetFolder = "src/main/java"
 * mapperName = "ArticleDAO"
 * mappingXMLPackage = "mapper"
 * mappingXMLTargetFolder = "src/main/resources"
 * tableName = "article"
 * domainObjectName = "Article"
 *
 *
 *
 * offsetLimit = true
 * comment = true
 * overrideXML = true
 * needToStringHashcodeEquals = false
 * needForUpdate = false
 * annotationDAO = true
 * annotation = false
 * useActualColumnNames = false
 * useExample = false
 * generateKeys = ""
 * encoding = "UTF-8"
 * useTableNameAlias = false
 * useDAOExtendStyle = false
 * useSchemaPrefix = false
 * jsr310Support = false
 */

@Getter
@Setter
public class GeneratorConfig {

    /**
     * 本配置的名称
     */
    private String name;

    private String connectorJarPath;

    private String projectFolder;

    private String modelPackage;

    private String modelPackageTargetFolder;

    private String daoPackage;

    private String daoTargetFolder;

    private String mapperName;

    private String mappingXMLPackage;

    private String mappingXMLTargetFolder;

    private String tableName;


    private String domainObjectName;

    private boolean offsetLimit;

    private boolean comment;

    private boolean overrideXML;

    private boolean needToStringHashcodeEquals;

    private boolean needForUpdate;

    private boolean annotationDAO;

    private boolean annotation;

    private boolean useActualColumnNames;

    private boolean useExample;

    private String generateKeys;

    private String encoding;

    private boolean useTableNameAlias;

    private boolean useDAOExtendStyle;

    private boolean useSchemaPrefix;

    private boolean jsr310Support;

    //todo 1 新加ui 字段  生成get setter 方法(使用lombok 不用再手动生成)
    /**
     * 项目基础包名
     */
    private String basePackage;
    /**
     * controller 包名
     */
    private String controller;
    private String service;
    /**
     * 通用mapper
     */
    private String tkCommonMapper;
    /**
     * ftl 模板所在目录
     */
    private String ftlTemplateFolder;
    /**
     * 是否生成core包
     */
    private boolean corePackageFlag;

    public boolean isUseTableNameAlias() {
        return useTableNameAlias;
    }

    public boolean isCorePackageFlag() {
        return corePackageFlag;
    }
}
