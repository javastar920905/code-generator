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
                            render: (h, params) => {
                                return h('div', [
                                    h('Button', {
                                        props: {
                                            type: 'primary',
                                            size: 'small'
                                        }
                                    }, '应用'),
                                    h('Button', {
                                        props: {
                                            type: 'error',
                                            size: 'small'
                                        }
                                    }, '删除')
                                ]);
                            }
                        }
                    ],
                    data: [
                        {
                            name: 'John Brown',
                            age: 18,
                        },
                    ]
                }
            }
        },
        watch: {
            configShow(val) {
                this.$emit("update:configVisible", val)
            },
            configVisible(val) {
                this.configShow = val
            }
        }
    }
</script>