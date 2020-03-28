import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import Routers from './router.js';
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//在一个模块化工程中使用它，必须要通过 Vue.use() 明确地安装路由功能
Vue.use(VueRouter);
Vue.use(iView);

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
