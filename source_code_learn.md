# generator 源码学习
## 如何学习源码
- 根据文档学习日常使用
    - xml 生成代码
    - 配置说明了解
- 阅读大牛发布的源码总结
    - https://blog.csdn.net/cx105200/article/details/80254542
    - 快速开始
       -  环境配置 git clone https://github.com/mybatis/generator.git
       -  生成代码  genrator 的使用,找到程序入口 (META-INF/MANIFEST.MF,Main-Class: org.mybatis.generator.api.ShellRunner)
       ```yaml
          List<String> warnings = new ArrayList<String>();
          boolean overwrite = true; 
          Resource xmlConfigResource = new ClassPathResource("MybatisGeneratorConfig.xml");
          File configFile = xmlConfigResource.getFile();
          ConfigurationParser cp = new ConfigurationParser(warnings);
          Configuration config = cp.parseConfiguration(configFile);
          DefaultShellCallback callback = new DefaultShellCallback(overwrite);
          MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
          myBatisGenerator.generate(null);
       ```
       -  代码生成器,执行流程
       ```
           Connecting to the Database
           Introspecting table hello
           Generating Example class for table hello
           Generating Record class for table hello
           Generating Mapper Interface for table hello
           Generating SQL Map for table hello
           Saving file HelloMapper.xml
           Saving file HelloExample.java
           Saving file Hello.java
           Saving file HelloMapper.java
           MyBatis Generator finished successfully.
       ```
    - 源码修改
       -  编码原则: 对外提供服务的只能是API,与API（对外提供服务）无关的那么是不允许对外提供访问方式的
       -  修改配置文件
       -  加入解析器
       -  修改DTD校验文件
       -  新建解析器
       -  获取表数据
       -  生成文件基础信息
       -  生成资源生成器
       -  保存资源文件信息
    - 自定义插件
       -  新建插件
       -  应用插件
       -  执行
    - 分析源码，我们需要知道程序入口
- 根据官方文档了解 项目模块划分 core
- 了解入口文件 MyBatisGenerator,了解类继承体系



##


##