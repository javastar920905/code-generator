# generater-ui-vue
## 代码生成器的页面 模块,vue 项目
- 创建 vue 前端项目 
    - cnpm install -g @vue/cli
    - vue create generater-ui-vue 
    - cd  generater-ui-vue 
    - cnpm install 
- cnpm run build 把前端页面发布的 spring boot 项目中
    - 把 dist 目录 html文件放入 generator/src/main/resources/templates中
    - 把css/img/js 目录放在     generator/src/main/resources/static 中
    
## 项目依赖和版本 
- 具体查看 package.json dependencies{...}
- vue-router 路由
- iview ui 组件
- cnpm install axios --save   --save 会自动把依赖写入,package.json

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
