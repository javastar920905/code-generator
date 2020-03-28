<style scoped>
    .layout{
        border: 1px solid #d7dde4;
        background: #f5f7f9;
        position: relative;
        border-radius: 4px;
        overflow: hidden;
    }
    .layout-logo{
        width: 100px;
        height: 30px;
        background: #5b6270;
        border-radius: 3px;
        float: left;
        position: relative;
        top: 15px;
        left: 20px;
    }
    .layout-nav{
        width: 420px;
        margin: 0 auto;
        margin-right: 20px;
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
                            <Icon type="ios-paper-outline" />
                            配置
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
            <Layout>
                <Sider hide-trigger :style="{background: '#fff'}">
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
                        <Submenu name="1">
                            <template slot="title">
                                <Icon type="ios-navigate"></Icon>
                                Item 1
                            </template>
                            <MenuItem name="1-1">Option 1</MenuItem>
                            <MenuItem name="1-2">Option 2</MenuItem>
                            <MenuItem name="1-3">Option 3</MenuItem>
                        </Submenu>
                        <Submenu name="2">
                            <template slot="title">
                                <Icon type="ios-keypad"></Icon>
                                Item 2
                            </template>
                            <MenuItem name="2-1">Option 1</MenuItem>
                            <MenuItem name="2-2">Option 2</MenuItem>
                        </Submenu>
                        <Submenu name="3">
                            <template slot="title">
                                <Icon type="ios-analytics"></Icon>
                                Item 3
                            </template>
                            <MenuItem name="3-1">Option 1</MenuItem>
                            <MenuItem name="3-2">Option 2</MenuItem>
                        </Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <Breadcrumb :style="{margin: '24px 0'}">
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Components</BreadcrumbItem>
                        <BreadcrumbItem>Layout</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{padding: '24px', minHeight: '280px', background: '#fff'}">
                        <config-detail></config-detail>
                    </Content>
                </Layout>
            </Layout>
        </Layout>
        <DBConfig :drawerVisible.sync="drawerVisible"></DBConfig>
        <!--配置-->
        <config-modal :configVisible.sync="configVisible"></config-modal>

    </div>
</template>
<script>
    import DBConfig from './DBConfig'
    import ConfigModal from './ConfigModal'
    import ConfigDetail from './ConfigDetail'
    export default {
        components:{
            DBConfig,
            ConfigModal,
            ConfigDetail
        },
        data() {
            return {
                drawerVisible: false,
                configVisible: false
            }
        },
        methods: {
            selectOperation(name) {
                if (name == 1) {
                    /*新建数据库连接*/
                    this.drawerVisible = true
                } else {
                    this.configVisible = true
                }
            }
        }

    }
</script>
<style>
    .ivu-btn {
        margin-right: 10px;
    }
</style>