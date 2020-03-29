package cn.javabus.generator.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * <p>
 * <p>
 * <p>
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
@ApiModel
@Getter
@Setter
public class GeneratorConfig {

    /**
     * 本配置的名称  命名建议 公司_项目_模块
     */
    @ApiModelProperty(value = "本配置的名称  命名建议 公司_项目_模块", required = true)
    private String name;

    @ApiModelProperty(required = false,hidden = true)
    private String connectorJarPath;

    @ApiModelProperty("需要生成代码的项目,所在目录")
    private String projectFolder;

    @ApiModelProperty("实体类包 com.company.project.entity")
    private String modelPackage;

    @ApiModelProperty("src/main/java")
    private String modelPackageTargetFolder;

    @ApiModelProperty("com.company.project.dao")
    private String daoPackage;

    @ApiModelProperty("src/main/java")
    private String daoTargetFolder;

    @ApiModelProperty("ArticleDAO")
    private String mapperName;

    @ApiModelProperty("mapper")
    private String mappingXMLPackage;

    @ApiModelProperty("src/main/resources")
    private String mappingXMLTargetFolder;

    //数据库表名称
    @ApiModelProperty("数据库表名称")
    private String tableName;


    //类名
    @ApiModelProperty("类名")
    private String domainObjectName;

    @ApiModelProperty("")
    private boolean offsetLimit;

    @ApiModelProperty("")
    private boolean comment;

    @ApiModelProperty("")
    private boolean overrideXML;

    @ApiModelProperty("")
    private boolean needToStringHashcodeEquals;

    @ApiModelProperty("")
    private boolean needForUpdate;

    @ApiModelProperty("")
    private boolean annotationDAO;

    @ApiModelProperty("")
    private boolean annotation;

    @ApiModelProperty("")
    private boolean useActualColumnNames;

    @ApiModelProperty("")
    private boolean useExample;

    @ApiModelProperty("")
    private String generateKeys;

    @ApiModelProperty("UTF-8")
    private String encoding;

    @ApiModelProperty("")
    private boolean useTableNameAlias;

    @ApiModelProperty("")
    private boolean useDAOExtendStyle;

    @ApiModelProperty("")
    private boolean useSchemaPrefix;

    @ApiModelProperty("")
    private boolean jsr310Support;

    //todo 1 新加ui 字段  生成get setter 方法(使用lombok 不用再手动生成)
    /**
     * 项目基础包名
     */
    @ApiModelProperty("项目基础包名")
    private String basePackage;
    /**
     * controller 包名
     */
    @ApiModelProperty("controller 包名")
    private String controller;
    @ApiModelProperty("service 包名")
    private String service;
    /**
     * 通用mapper
     */
    @ApiModelProperty("通用mapper tk.mybatis.mapper.common.Mapper")
    private String tkCommonMapper;
    /**
     * ftl 模板所在目录
     */
    @ApiModelProperty("ftl 模板所在目录")
    private String ftlTemplateFolder;
    /**
     * 是否生成core包
     */
    @ApiModelProperty("是否生成core包")
    private boolean corePackageFlag;

    public boolean isUseTableNameAlias() {
        return useTableNameAlias;
    }

    public boolean isCorePackageFlag() {
        return corePackageFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectorJarPath() {
        return connectorJarPath;
    }

    public void setConnectorJarPath(String connectorJarPath) {
        this.connectorJarPath = connectorJarPath;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getModelPackageTargetFolder() {
        return modelPackageTargetFolder;
    }

    public void setModelPackageTargetFolder(String modelPackageTargetFolder) {
        this.modelPackageTargetFolder = modelPackageTargetFolder;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getDaoTargetFolder() {
        return daoTargetFolder;
    }

    public void setDaoTargetFolder(String daoTargetFolder) {
        this.daoTargetFolder = daoTargetFolder;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getMappingXMLPackage() {
        return mappingXMLPackage;
    }

    public void setMappingXMLPackage(String mappingXMLPackage) {
        this.mappingXMLPackage = mappingXMLPackage;
    }

    public String getMappingXMLTargetFolder() {
        return mappingXMLTargetFolder;
    }

    public void setMappingXMLTargetFolder(String mappingXMLTargetFolder) {
        this.mappingXMLTargetFolder = mappingXMLTargetFolder;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public boolean isOffsetLimit() {
        return offsetLimit;
    }

    public void setOffsetLimit(boolean offsetLimit) {
        this.offsetLimit = offsetLimit;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isOverrideXML() {
        return overrideXML;
    }

    public void setOverrideXML(boolean overrideXML) {
        this.overrideXML = overrideXML;
    }

    public boolean isNeedToStringHashcodeEquals() {
        return needToStringHashcodeEquals;
    }

    public void setNeedToStringHashcodeEquals(boolean needToStringHashcodeEquals) {
        this.needToStringHashcodeEquals = needToStringHashcodeEquals;
    }

    public boolean isNeedForUpdate() {
        return needForUpdate;
    }

    public void setNeedForUpdate(boolean needForUpdate) {
        this.needForUpdate = needForUpdate;
    }

    public boolean isAnnotationDAO() {
        return annotationDAO;
    }

    public void setAnnotationDAO(boolean annotationDAO) {
        this.annotationDAO = annotationDAO;
    }

    public boolean isAnnotation() {
        return annotation;
    }

    public void setAnnotation(boolean annotation) {
        this.annotation = annotation;
    }

    public boolean isUseActualColumnNames() {
        return useActualColumnNames;
    }

    public void setUseActualColumnNames(boolean useActualColumnNames) {
        this.useActualColumnNames = useActualColumnNames;
    }

    public boolean isUseExample() {
        return useExample;
    }

    public void setUseExample(boolean useExample) {
        this.useExample = useExample;
    }

    public String getGenerateKeys() {
        return generateKeys;
    }

    public void setGenerateKeys(String generateKeys) {
        this.generateKeys = generateKeys;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setUseTableNameAlias(boolean useTableNameAlias) {
        this.useTableNameAlias = useTableNameAlias;
    }

    public boolean isUseDAOExtendStyle() {
        return useDAOExtendStyle;
    }

    public void setUseDAOExtendStyle(boolean useDAOExtendStyle) {
        this.useDAOExtendStyle = useDAOExtendStyle;
    }

    public boolean isUseSchemaPrefix() {
        return useSchemaPrefix;
    }

    public void setUseSchemaPrefix(boolean useSchemaPrefix) {
        this.useSchemaPrefix = useSchemaPrefix;
    }

    public boolean isJsr310Support() {
        return jsr310Support;
    }

    public void setJsr310Support(boolean jsr310Support) {
        this.jsr310Support = jsr310Support;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTkCommonMapper() {
        return tkCommonMapper;
    }

    public void setTkCommonMapper(String tkCommonMapper) {
        this.tkCommonMapper = tkCommonMapper;
    }

    public String getFtlTemplateFolder() {
        return ftlTemplateFolder;
    }

    public void setFtlTemplateFolder(String ftlTemplateFolder) {
        this.ftlTemplateFolder = ftlTemplateFolder;
    }

    public void setCorePackageFlag(boolean corePackageFlag) {
        this.corePackageFlag = corePackageFlag;
    }
}
