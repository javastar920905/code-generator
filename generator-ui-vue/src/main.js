import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import Routers from './router.js';
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import axios from 'axios';

//在一个模块化工程中使用它，必须要通过 Vue.use() 明确地安装路由功能
Vue.use(VueRouter);
Vue.use(iView);
Vue.use(axios)
// let conf = {
//     method: 'post', // default,
//     responseType: 'json', // default 返回数据类型
//     timeout: 5000,//设置超时时间
//     params: {//返回数据类型
//         timestamp: Date.parse(new Date()) / 1000
//     },
//     proxy: {
//         host: '127.0.0.1',
//         port: 8080
//     },
// }
//axios 作为全局变量使用
axios.defaults.baseURL = 'http://127.0.0.1:8080/';
axios.defaults.headers.common['Authorization'] = 'AUTH_TOKEN';
//Using application/x-www-form-urlencoded format
//axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'; //默认是 json 方式提交
//const qs = require('qs');
//axios.post('/foo', qs.stringify({ 'bar': 123 }));


Vue.prototype.axios = axios;
Vue.prototype.msg = function (response) {
    if (response.data==null){
        console.log(response)
        this.$Message.error(response.message);
        return;
    }
    if (response.data.code == 200) {
        this.$Message.success(response.data.message)
    } else {
        this.$Message.error(response.data.message)
    }
};
Vue.prototype.errMsg = function (error) {
    this.$Message.error(error.message)
};
Vue.prototype.okMsg = function (msg) {
    this.$Message.success(msg)
};

//定义$eventHub 用于接收广播事件
Vue.prototype.$eventHub= Vue.prototype.$eventHub || new Vue();
// 调用$emit 广播事件  this.$eventHub.$emit('你的事件名字', 你的需要传送的数据)
// 事件接收者 this.$eventHub.$on('你的事件名字', (val)=>{ handle(yourData)} )
// 关闭广播  我们主要通过 $off(你的事件名字)来取消收听



Vue.config.productionTip = false
// The routing configuration
const RouterConfig = {
    routes: Routers
};
const router = new VueRouter(RouterConfig);

new Vue({
    el: '#app',
    router: router,
    render: h => h(App)
});

// new Vue({
//   render: h => h(App),
// }).$mount('#app')
