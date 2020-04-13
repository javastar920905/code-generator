package cn.javabus.generator.plugins;

import cn.javabus.generator.util.MyStringUtils;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author ou.zhenxing on 2020-03-31.
 * 生成鼎城项目的 dao 实现类(这个插件是为了兼容项目中的 ibatis 写的)
 */
public class DCDaoImplPlugin extends PluginAdapter {
    static  final String suffix_Dao = "DAO";
    static final String suffix_DaoImpl = "DAOImpl";
    static final String BaseDaoImplFullPath = "com.eagle.life.db.base.dao.LifeBaseDaoImpl";
    //final String BaseDaoImplFullPath = "cn.javabus.generator.test.LifeBaseDaoImpl";
    private static final FullyQualifiedJavaType PARAM_ANNOTATION_TYPE = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param");
    private static final FullyQualifiedJavaType LIST_TYPE = FullyQualifiedJavaType.getNewListInstance();
    private static final FullyQualifiedJavaType SERIALIZEBLE_TYPE = new FullyQualifiedJavaType("java.io.Serializable");

    private List<Method> methods = new ArrayList<>();

    private ShellCallback shellCallback = null;

    //调用构造器
    public DCDaoImplPlugin() {
        shellCallback = new DefaultShellCallback(false);
    }

    private boolean isUseExample() {
        return "true".equals(getProperties().getProperty("useExample"));
    }

    private  static  String getModuleName(String daoPackage){
        int idx=daoPackage.indexOf(".db.");
        String temp= daoPackage.substring(0,idx);
        return  temp.replace("com.eagle.","");
    }


    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    @Override
    public void initialized(IntrospectedTable introspectedTable) {//internalAttributes 属性有 36 个值
        String daoInterfaceType = introspectedTable.getDAOInterfaceType();
        introspectedTable.setDAOInterfaceType(daoInterfaceType);//有效了, 本身返回就是 DAO
        introspectedTable.setDAOImplementationType(daoInterfaceType);
        introspectedTable.setMyBatis3JavaMapperType(daoInterfaceType);//有效了,到生命周期最开始时设置有效
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(daoInterfaceType);//有效, 修改 Mapper文件 为 Dao
        super.initialized(introspectedTable);
    }

    /**
     * protected LifeProductRateExample(LifeProductRateExample example) {
     * this.orderByClause = example.orderByClause;
     * this.oredCriteria = example.oredCriteria;
     * }
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String exampleFullName = introspectedTable.getExampleType(); //cn.javabus.generator.ok.model.lifeProductExample
        String modelTargetPackage = context.getJavaModelGeneratorConfiguration().getTargetPackage();
        String exampleShortName = exampleFullName.replace(modelTargetPackage + ".", "");
        //1.2 添加构造器,构造函数,构造方法
        Method construct = new Method(exampleShortName);
        construct.setVisibility(JavaVisibility.PUBLIC);
        construct.setConstructor(true);
        construct.addParameter(new Parameter(new FullyQualifiedJavaType(exampleShortName), "example"));
        construct.addBodyLine("this.orderByClause = example.orderByClause;");
        construct.addBodyLine("this.oredCriteria = example.oredCriteria;");
        topLevelClass.addMethod(construct);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String daoInterfaceType = introspectedTable.getDAOInterfaceType();
        introspectedTable.setDAOInterfaceType(daoInterfaceType);//无效, 本身返回就是 DAO
        introspectedTable.setMyBatis3JavaMapperType(daoInterfaceType);//无效,
        introspectedTable.setMyBatis3FallbackSqlMapNamespace(daoInterfaceType);
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        introspectedTable.setMyBatis3XmlMapperFileName(tableName+"_SqlMap.xml");

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }


    /**
     * CompilationUnit 抽象类,提供三个实现类(Interface  创建接口文件 ,TopLevelClass  创建 class 文件,TopLevelEnumeration 创建枚举文件)
     * 执行顺序 initialized> 实体类>example>countByxxx>updatexx>clientGenerated>contextGenerateAdditionalJavaFiles
     *
     * @param introspectedTable
     * @return
     */
    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        boolean hasPk = introspectedTable.hasPrimaryKeyColumns();//是否存在主键列
        JavaFormatter javaFormatter = context.getJavaFormatter();

        //获取 MybatisGeneratorConfig.xml 配置内容
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = context.getJavaClientGeneratorConfiguration();
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
//        ModelType defaultModelType = context.getDefaultModelType();//flat
//        CommentGeneratorConfiguration commentGeneratorConfiguration = context.getCommentGeneratorConfiguration();
//        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();////获取数据库表生成配置

        String daoTargetDir = javaClientGeneratorConfiguration.getTargetProject();// src/main/java
        String daoTargetPackage = javaClientGeneratorConfiguration.getTargetPackage();// cn.javabus.generator.test.dao
        String modelTargetPackage = javaModelGeneratorConfiguration.getTargetPackage();// cn.javabus.generator.test.model
        List<GeneratedJavaFile> mapperJavaFiles = new ArrayList<>();
        String javaFileEncoding = context.getProperty("javaFileEncoding");//null
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String daoInterfaceType = introspectedTable.getDAOInterfaceType();//cn.javabus.generator.test.dao.lifeProductDAO
        String exampleFullName = introspectedTable.getExampleType(); //cn.javabus.generator.ok.model.lifeProductExample
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();

        String daoImplName = domainName + suffix_DaoImpl;
        String fullDaoImpleName = daoTargetPackage + ".impl." + daoImplName;


        //com.eagle.life.db.base.dao.LifeBaseDaoImpl  (LifeBaseDaoImpl 根据模块动态改变)
        String moduleName=getModuleName(daoTargetPackage);
       String BaseDaoImplFullPath="com.eagle."+moduleName+".db.base.dao."+MyStringUtils.upperFirstWord(moduleName)+"BaseDaoImpl";
        //1 创建类 包名+实体类+DAOImpl
        //声明父接口类  Interface mapperInterface = new Interface(daoTargetPackage + domainName + suffix_DaoImpl);
        TopLevelClass clazz = new TopLevelClass(fullDaoImpleName);
        clazz.setSuperClass(BaseDaoImplFullPath);

        //1.2 添加构造器,构造函数,构造方法
        Method construct = new Method(daoImplName);
        construct.setVisibility(JavaVisibility.PUBLIC);
        construct.setConstructor(true);
        construct.addBodyLine("super();");
        clazz.addMethod(construct);

        if (stringHasValue(daoTargetPackage)) {
            //2 导入类
            clazz.addImportedType(LIST_TYPE);//java.util.List
            clazz.addImportedType(daoInterfaceType);
            clazz.addImportedType(exampleFullName);//
            clazz.addImportedType(BaseDaoImplFullPath);//
            clazz.addImportedType(modelTargetPackage + "." + domainName);//导入实体类
            // clazz.addImportedType(PARAM_ANNOTATION_TYPE);//org.apache.ibatis.annotations.Param
            // clazz.addImportedType(SERIALIZEBLE_TYPE);//java.io.Serializable

            clazz.setVisibility(JavaVisibility.PUBLIC);
            //3 添加类注释
            clazz.addJavaDocLine("/**");
            String datestr = DateFormat.getDateInstance().format(new Date());
            clazz.addJavaDocLine(" * " + " created by cn.javabus.generator.plugins.DCDaoImplPlugin on " + datestr);
            clazz.addJavaDocLine(" */");


            // 5 添加实现类implements 实体类名DAO {
            clazz.addSuperInterface(new FullyQualifiedJavaType(daoTargetPackage + "." + domainName + suffix_Dao));

            if (!this.methods.isEmpty()) {
                for (Method method : methods) {
                    clazz.addMethod(method);
                }
            }


            // 6 生成内部类
            clazz.addInnerClass(customStaticClass(introspectedTable));
            // 7 生成类文件  mapperJavafile y已经是类字符串了
            GeneratedJavaFile mapperJavafile = new GeneratedJavaFile(clazz, daoTargetDir, javaFileEncoding, javaFormatter);

            //8 其他任务
            customDao(introspectedTable);
            try {

                File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);//检查 dao 路径是否存在
                File mapperFile = new File(mapperDir, mapperJavafile.getFileName());
                // 文件不存在
                if (!mapperFile.exists()) {
                    mapperJavaFiles.add(mapperJavafile);
                }
            } catch (ShellException e) {
                e.printStackTrace();
            }
        }
        return mapperJavaFiles;
    }


    private void customDao(IntrospectedTable introspectedTable) {
        String daoTargetDir = context.getJavaClientGeneratorConfiguration().getTargetProject();// src/main/java
        String daoTargetPackage = context.getJavaClientGeneratorConfiguration().getTargetPackage();// cn.javabus.generator.test.dao
        List<GeneratedJavaFile> generatedJavaFiles = introspectedTable.getGeneratedJavaFiles();
        for (GeneratedJavaFile generatedJavaFile : generatedJavaFiles) {
            // generatedJavaFile.getCompilationUnit() 这里已经生成完整的代码了
            CompilationUnit compilationUnit = generatedJavaFile.getCompilationUnit();
            FullyQualifiedJavaType type = compilationUnit.getType();
            String modelName = type.getShortName();
            if (modelName.contains("DAO")) {
                try {
                    File mapperDir = shellCallback.getDirectory(daoTargetDir, daoTargetPackage);//检查 dao 路径是否存在
                    File daoFile = new File(mapperDir, generatedJavaFile.getFileName());
//                    if (!daoFile.exists()) {
//                        throw new IllegalArgumentException(daoFile.getName() + " 不存在!" + daoFile.getAbsolutePath());
//                    }
                    daoFielPath=daoFile.getPath();

                } catch (ShellException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //记录生成dao 文件的地址,用于强制重写
    public static String  daoFielPath=null;



    /**
     * private static class UpdateByExampleParms extends ElecpolPolicyExample {
     * private Object record;
     * <p>
     * public UpdateByExampleParms(Object record, ElecpolPolicyExample example) {
     * super(example);
     * this.record = record;
     * }
     * <p>
     * public Object getRecord() {
     * return record;
     * }
     * }
     *
     * @return
     */
    private InnerClass customStaticClass(IntrospectedTable introspectedTable) {
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        FullyQualifiedJavaType object = new FullyQualifiedJavaType("Object");
        String exampleFullName = introspectedTable.getExampleType();
        String shortExampleName = domainName + "Example";
        //1 创建内部静态类
        InnerClass innerClass = new InnerClass("UpdateByExampleParms");
        innerClass.setVisibility(JavaVisibility.PRIVATE);
        innerClass.setStatic(true);
        innerClass.setSuperClass(shortExampleName);

        //2 添加字段
        Field field = new Field("record", object);
        field.setVisibility(JavaVisibility.PRIVATE);
        innerClass.addField(field);

        //3 添加构造器,构造函数,构造方法
        Method construct = new Method("UpdateByExampleParms");
        construct.setVisibility(JavaVisibility.PUBLIC);
        construct.setConstructor(true);
        construct.addParameter(0, new Parameter(object, "record"));
        construct.addParameter(1, new Parameter(new FullyQualifiedJavaType(exampleFullName), "example"));
        construct.addBodyLine("super(example);");
        construct.addBodyLine("this.record = record;");
        innerClass.addMethod(construct);

        //4 添加方法
        Method method = new Method("getRecord");
        method.addBodyLine("return record;");
        method.setReturnType(object);
        method.setVisibility(JavaVisibility.PUBLIC);
        innerClass.addMethod(method);
        return innerClass;
    }

    /**
     * todo 注意!!!  所有方法只调用了 Interface interfaze, 参数的;
     * public int countByExample(ElecpolPolicyExample example) {
     * Integer count = (Integer)  this.queryForObject("elecpol_policy.ibatorgenerated_countByExample", example);
     * return count;
     * }
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine("Integer count = (Integer)  this.queryForObject(\"" + tableName + ".countByExample\", example);");
        methodImpl.addBodyLine("return count;");
        this.methods.add(methodImpl);

        method.setReturnType(new FullyQualifiedJavaType("int"));
        return super.clientCountByExampleMethodGenerated(method, interfaze, introspectedTable);
    }

    private Method buildNewMethodImpl(Method method) {
        Method methodImpl = new Method(method.getName());
        methodImpl.addAnnotation("@Override");
        methodImpl.setVisibility(JavaVisibility.PUBLIC);
        List<Parameter> parameters = method.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            Parameter p = new Parameter(parameter.getType(), parameter.getName());//移除@param注解
            methodImpl.addParameter(p);
        }
        return methodImpl;
    }

    //重新定义接口的的方法,移除@param注解
    private Method buildNewMethod(Method method) {
        Method method1 = new Method(method.getName());
        List<Parameter> parameters = method.getParameters();
        for (int i = 0; i < parameters.size(); i++) {
            Parameter parameter = parameters.get(i);
            Parameter p = new Parameter(parameter.getType(), parameter.getName());//移除@param注解
            method1.addParameter(p);
        }
        return method1;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine("Integer rows = (Integer)  this.deleteObject(\"" + tableName + ".deleteByExample\", example);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        return super.clientDeleteByExampleMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * public int deleteByPrimaryKey(Long id) {
     * ElecpolPolicy key = new ElecpolPolicy();
     * key.setId(id);
     * int rows = this.deleteObject("elecpol_policy.ibatorgenerated_deleteByPrimaryKey", key);
     * return rows;
     * }
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);
        List<Parameter> parameters = method.getParameters();
        String keyName = parameters.get(0).getName();
        String keySetMethodName = "set" + MyStringUtils.upperFirstWord(keyName);//setId 首字母大写

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine(domainName + " key = new " + domainName + "();");//ElecpolPolicy key = new ElecpolPolicy();
        methodImpl.addBodyLine("key." + keySetMethodName + "(" + keyName + ");");//key.setId(id);
        methodImpl.addBodyLine("int rows = this.deleteObject(\"" + tableName + ".deleteByPrimaryKey\", key);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }




    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("Long"));
        method.setReturnType(new FullyQualifiedJavaType("Long"));
        methodImpl.addBodyLine("Object newKey = this.saveObject(\"" + tableName + ".insert\", record);");
        methodImpl.addBodyLine("return (Long) newKey;");
        this.methods.add(methodImpl);
        return super.clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("Long"));
        method.setReturnType(new FullyQualifiedJavaType("Long"));
        methodImpl.addBodyLine("Object newKey = this.saveObject(\"" + tableName + ".insertSelective\", record);");
        methodImpl.addBodyLine("return (Long) newKey;");
        this.methods.add(methodImpl);
        return super.clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    /**
     * public List<ElecpolPolicy> selectByExample(ElecpolPolicyExample example) {
     * List<ElecpolPolicy> list = this.queryForList("elecpol_policy.ibatorgenerated_selectByExample", example);
     * return list;
     * }
     *
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("List<" + domainName + ">"));
        method.setReturnType(new FullyQualifiedJavaType("List<" + domainName + ">"));
        methodImpl.addBodyLine("List<" + domainName + "> list = this.queryForList(\"" + tableName + ".selectByExample\", example);");
        methodImpl.addBodyLine("return list;");
        this.methods.add(methodImpl);
        return super.clientSelectByExampleWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }


    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);
        List<Parameter> parameters = method.getParameters();
        String keyName = parameters.get(0).getName();
        String keySetMethodName = "set" + MyStringUtils.upperFirstWord(keyName);//setId 首字母大写


        methodImpl.setReturnType(new FullyQualifiedJavaType(domainName));
        method.setReturnType(new FullyQualifiedJavaType(domainName));
        methodImpl.addBodyLine(domainName + " key = new " + domainName + "();");//ElecpolPolicy key = new ElecpolPolicy();
        methodImpl.addBodyLine("key." + keySetMethodName + "(" + keyName + ");");//key.setId(id);
        // ElecpolPolicy record = (ElecpolPolicy) this.queryForObject("elecpol_policy.ibatorgenerated_selectByPrimaryKey", key);
        methodImpl.addBodyLine(domainName + " record = (" + domainName + ") this.queryForObject(\"" + domainName + ".selectByPrimaryKey\", key);");
        methodImpl.addBodyLine("return record;");
        this.methods.add(methodImpl);
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Method method1 = buildNewMethod(method);//移除@param注解
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method1.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine("UpdateByExampleParms parms = new UpdateByExampleParms(record, example);");
        methodImpl.addBodyLine(" int rows = this.updateObject(\"" + tableName + ".updateByExampleSelective\", parms);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        return super.clientUpdateByExampleSelectiveMethodGenerated(method1, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Method method1 = buildNewMethod(method);//移除@param注解
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method1.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine("UpdateByExampleParms parms = new UpdateByExampleParms(record, example);");
        methodImpl.addBodyLine(" int rows = this.updateObject(\"" + tableName + ".updateByExample\", parms);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        //interfaze.addMethod(method1);
        return super.clientUpdateByExampleWithoutBLOBsMethodGenerated(method1, interfaze, introspectedTable);
    }


    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);
        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine(" int rows = this.updateObject(\"" + tableName + ".updateByPrimaryKey\", record);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        return super.clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }


    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();//表名称life_product
        String domainName = introspectedTable.getTableConfiguration().getDomainObjectName();
        Method methodImpl = buildNewMethodImpl(method);

        methodImpl.setReturnType(new FullyQualifiedJavaType("int"));
        method.setReturnType(new FullyQualifiedJavaType("int"));
        methodImpl.addBodyLine(" int rows = this.updateObject(\"" + tableName + ".updateByPrimaryKeySelective\", record);");
        methodImpl.addBodyLine("return rows;");
        this.methods.add(methodImpl);
        return super.clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }
}
