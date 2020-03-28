# 项目说明
## 项目来源
- https://github.com/javastar920905/mybatis-generator-gui
- 项目之前是基于 gui 页面的,二次开发难度比较大 (javafx_scenebuilder-下载用了我几个小时)
- 直接采用 web 页面,二次开发相对简单,可以部署在服务器对外提供服务,供团队成员使用(下载 zip 包形式)
## 功能说明

## 技术选型
- spring boot 2.2.6.RELEASE 快速集成页面,ui 组件
- 代码复用 

## 开发进度
- [ ] spring boot 集成页面整合 ui 组件
    - 引入 spring-boot-devtools,build 页面即可触发实时更新页面(前后端开发分离了就不需要了)
    - springboot推荐的页面模板是thymeleaf，在前后端不分离的情况下，springboot推荐用html做页面，然后用thymeleaf做模板引擎，做数据渲染，可以用js或者jquery手动去操作dom，这里用vue
        - ThymeleafProperties.java 查看 thymeleaf自动配置
        - private String prefix = "classpath:/templates/";
        - private String suffix = ".html";
    - 创建 vue 前端项目 
        - cnpm install -g @vue/cli
        - vue create generater-ui-vue 
        - cd  generater-ui-vue 
        - cnpm install 
        - cnpm run build 把 dist 目录文件放入 generator/src/main/resources/templates中即可
    - 引入 iview ui 组件,最近项目中刚好在用,就不换了 
    
- [ ] 代码复用,移植
- [ ] 前端页面布局