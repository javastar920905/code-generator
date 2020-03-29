# 目录结构介绍
* template  可自定义的ftl模板,

# ftl 模板使用方式
使用简单变量: ${basePackage}
使用对象变量 ${generatorConfig.controller}

# ftl 模板内置变量
## 简单变量
date: yyyy/MM/dd 格式
author: 类名上的作者名
modelNameUpperCamel: 大写的表名,如: Article
modelNameLowerCamel: 小写表名
basePackage: 项目基础包名,如: com.company.project

## 对象变量
generatorConfig :
  name = null
  connectorJarPath = null
  projectFolder = "E:\gitRepository\spring-boot-api-project-seed\serviceA-provider"
  tableName = "article"
  controller="com.company.project.controller"
  service="com.company.project.service"
  modelPackage = "com.company.project.entity"
  daoPackage = "com.company.project.dao"
  domainObjectName = "Article"
  mappingXMLPackage = "mapper"
  mapperName = "ArticleDAO"
  daoTargetFolder = "src/main/java"
  modelPackageTargetFolder = "src/main/java"
  mappingXMLTargetFolder = "src/main/resources"

  offsetLimit = true
  comment = true
  overrideXML = true
  needToStringHashcodeEquals = false
  needForUpdate = false
  annotationDAO = true
  annotation = false
  useActualColumnNames = false
  useExample = false
  generateKeys = ""
  encoding = "UTF-8"
  useTableNameAlias = false
  useDAOExtendStyle = false
  useSchemaPrefix = false
  jsr310Support = false

