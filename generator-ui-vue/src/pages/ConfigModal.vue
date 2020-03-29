<template>
    <Drawer
            title="配置"
            v-model="configShow"
            width="720"
            :mask-closable="false"
    >
        <Table border :columns="configTable.columns" :data="configTable.data"></Table>
    </Drawer>
</template>
<script>
    export default {
        props: {
            configVisible: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                configShow: false,
                configTable: {
                    columns: [
                        {
                            title: '配置名称',
                            key: 'name',
                        },
                        {
                            title: '操作',
                            key: 'action',
                            render: (h, { row, index}) => {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'primary',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.$eventHub.$emit("applyGeneratorConfig",row.name)
                                                this.configShow=false;
                                            }
                                        }
                                    }, '应用'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        },
                                        on: {
                                            click: () => {
                                                this.axios.delete('/generator/delConfig?generatorConfigName='+row.name)
                                                    .then((response) => {
                                                        this.msg(response)
                                                        this.getGeneratorConfigList();
                                                    })
                                                    .catch((error) => {
                                                        this.errMsg(error)
                                                    })
                                            }
                                        }
                                    }, '删除')
                                ]);
                            }
                        }
                    ],
                    data: []//接收数据
                }
            }
        },
        mounted(){
            this.getGeneratorConfigList();
        },
        watch: {
            configShow(val) {
                this.$emit("update:configVisible", val)
            },
            configVisible(val) {
                this.configShow = val
            }
        },
        methods: {
            getGeneratorConfigList() {
                this.axios.get('/generator/getConfigList')
                    .then((response) => {
                        console.log(response)
                        this.configTable.data = response.data.data
                    })
                    .catch((error) => {
                        this.errMsg(error)
                    })
            },
        }
    }
</script>