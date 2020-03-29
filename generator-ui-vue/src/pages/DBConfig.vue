<template>
    <div>

        <Drawer
                title="新建数据库连接"
                v-model="openDrawer"
                width="720"
                :mask-closable="false"
                :styles="styles"
        >
            <Form ref="formData" :model="formData" :rules="ruleValidate" :label-width="120">
                <FormItem label="保存名称" prop="name">
                    <Input v-model="formData.name" placeholder="company_product"/>
                </FormItem>
                <FormItem label="数据库类型" prop="dbType">
                    <Select v-model="formData.dbType">
                        <Option value="mysql">mysql</Option>
                    </Select>
                </FormItem>
                <FormItem label="主机名或IP地址" prop="host">
                    <Input v-model="formData.host"/>
                </FormItem>
                <FormItem label="端口号" prop="port">
                    <Input v-model="formData.port"/>
                </FormItem>
                <FormItem label="用户名" prop="username">
                    <Input v-model="formData.username"/>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input v-model="formData.password"/>
                </FormItem>
                <FormItem label="Schema/数据库" prop="schema">
                    <Input v-model="formData.schema"/>
                </FormItem>

                <FormItem label="编码">
                    <Select v-model="formData.encoding">
                        <Option value="utf8">utf8</Option>
                    </Select>
                </FormItem>

            </Form>
            <div class="demo-drawer-footer">
                <Button type="primary" @click="handleSubmit('formData')">保存</Button>

            </div>
        </Drawer>
    </div>
</template>
<script>
    export default {
        props: {
            drawerVisible: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                openDrawer: this.drawerVisible,
                styles: {
                    height: 'calc(100% - 55px)',
                    overflow: 'auto',
                    paddingBottom: '53px',
                    position: 'static'
                },
                formData: {
                    sOverssh: false,
                    isUpdate: false,
                    id: "",
                    dbType: "mysql",
                    name: "db_test",
                    host: "10.30.0.9",
                    port: "3306",
                    schema: "dcmall_uat",
                    username: "malluat",
                    password: "malluat1234@",
                    encoding: "utf8",

                    lport: "",
                    rport: "",
                    sshPort: "",
                    sshHost: "",
                    sshUser: "",
                    sshPassword: "",
                },
                ruleValidate: {
                    name: [
                        {required: true, message: 'The name cannot be empty', trigger: 'blur'}
                    ],
                    host: [
                        {required: true, message: 'The host cannot be empty', trigger: 'blur'}
                    ],
                    schema: [
                        {required: true, message: 'schema cannot be empty', trigger: 'blur'}
                    ],
                    dbType: [
                        {required: true, message: 'Please select the dbType', trigger: 'change'}
                    ],
                    username: [
                        {required: true, message: 'username cannot be empty', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: 'password cannot be empty', trigger: 'blur'}
                    ]
                }
            }
        },
        watch: {
            openDrawer(val) {
                this.$emit("update:drawerVisible", val)
            },
            drawerVisible(val) {
                this.openDrawer = val
            },
        },
        methods: {
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.axios.post(
                            '/DBConfig/saveDBConfig',
                            this.formData
                        ).then((response) => {
                            if (response.data.code == 200) {
                                this.$Message.success(response.data.message);
                                this.openDrawer=false;
                                this.$eventHub.$emit("updateDbConfig", true)
                            } else {
                                this.$Message.error(response.data.message);
                            }
                        }).catch((error) => {
                            this.errMsg(error)
                        });
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
        }
    }
</script>
<style>
    .demo-drawer-footer {
        width: 100%;
        position: absolute;
        bottom: 0;
        left: 0;
        border-top: 1px solid #e8e8e8;
        padding: 10px 16px;
        text-align: right;
        background: #fff;
    }
</style>
