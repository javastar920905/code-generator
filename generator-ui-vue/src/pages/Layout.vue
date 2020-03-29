<style scoped>
    .layout {
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;
    }

    .layout-logo {
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
    }

    .layout-nav {
        width: 420px;
        margin: 0 auto;
        margin-right: 20px;
    }
    .ivu-menu-vertical .ivu-menu-item, .ivu-menu-vertical .ivu-menu-submenu-title {
        padding: 2px 24px;
    }
</style>
<template>
    <div class="layout">
        <Layout>
            <Header>
                <Menu mode="horizontal" theme="dark" active-name="1" @on-select="selectOperation">
                    <div class="layout-logo"><img src="../assets/logo.png" height="30" width="30"/></div>
                    <div class="layout-nav">
                        <MenuItem name="1">
                            <Icon type="md-add-circle"/>
                            新建数据库连接
                        </MenuItem>
                        <MenuItem name="2">
                            <Icon type="ios-paper-outline"/>
                            已保存的配置
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
            <Layout>
                <Sider hide-trigger :style="{background: '#fff'}" width="300">
                    <Menu theme="light" width="auto" accordion @on-open-change="getSubNavList"
                          @on-select="selectMenuItem">
                        <Submenu v-for="(item, index) in navList" :key="index" :name="item.name">
                            <template slot="title">
                                <Icon tyoe="ios-navigate"></Icon>
                                {{item.name}}
                            </template>
                            <template v-if="navName===item.name">
                                <MenuItem :name="subItem" v-for="(subItem, subIndex) in subNavList"
                                          :key="subIndex+'sub'">
                                    {{subItem}}
                                </MenuItem>
                            </template>

                        </Submenu>

                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <!--<Breadcrumb :style="{margin: '24px 0'}">-->
                        <!--<BreadcrumbItem>Home</BreadcrumbItem>-->
                        <!--<BreadcrumbItem>Components</BreadcrumbItem>-->
                        <!--<BreadcrumbItem>Layout</BreadcrumbItem>-->
                    <!--</Breadcrumb>-->
                    <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
                        <!--传递选中表名称给子组件-->
                        <config-detail :parentSelectedTableName.sync="selectedTableName"></config-detail>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
        <!--dbconfigUpdated 接收子组件的广播事件-->
        <DBConfig :drawerVisible.sync="drawerVisible" ></DBConfig>
        <!--配置-->
        <config-modal :configVisible.sync="configVisible"></config-modal>

    </div>
</template>
<script>
    import DBConfig from './DBConfig'
    import ConfigModal from './ConfigModal'
    import ConfigDetail from './GeneratorConfigDetail'

    export default {
        components: {
            DBConfig,
            ConfigModal,
            ConfigDetail
        },
        data() {
            return {
                drawerVisible: false,
                configVisible: false,
                navList: [],
                subNavList: [],
                navName: "",
                selectedTableName:"",//当前选中数据库表名称

            }
        },
        mounted() {
            this.getDBConfigList()
            var that=this;

            //监听事件
            this.$eventHub.$on('updateDbConfig', (val)=>{
                console.log('广播传过来的值是'+val);
                that.getDBConfigList()
            } )


        },

        methods: {
            selectOperation(name) {
                if (name == 1) {
                    /*新建数据库连接*/
                    this.drawerVisible = true
                } else {
                    this.configVisible = true
                }
            },
            getDBConfigList() {
                this.axios.get('/DBConfig/getDBConfigList')
                    .then((response) => {
                        // handle success
                        this.navList = response.data.data
                    })
                    .catch((error) => {
                        this.errMsg(error)
                    })
            },
            getSubNavList(name) {//加载指定数据库 的所有数据表
                this.navName = name[0]
                this.axios.get('/DBConfig/getTablesByDBConfig?dBConfigName=' + name[0])
                    .then((response) => {
                        this.subNavList = response.data.data
                    })
                    .catch((error) => {
                        this.errMsg(error)
                    })
            },
            selectMenuItem(name) {//选中数据表名称
                this.selectedTableName=name;
                console.log(name)
            }

        }

    }
</script>
<style>
    .ivu-btn {
        margin-right: 10px;
    }
</style>