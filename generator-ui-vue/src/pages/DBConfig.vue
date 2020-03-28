<template>
    <div>

        <Drawer
            title="新建数据库连接"
            v-model="openDrawer"
            width="720"
            :mask-closable="false"
            :styles="styles"
        >
            <Form :model="formData" :label-width="100">
                <FormItem label="保存名称" >
                    <Input v-model="formData.name" />
                </FormItem>
                <FormItem label="数据库类型" >
                    <Select v-model="formData.owner">
                        <Option value="jobs">Steven Paul Jobs</Option>
                        <Option value="ive">Sir Jonathan Paul Ive</Option>
                    </Select>
                </FormItem>
                <FormItem label="主机名或IP地址" >
                    <Input v-model="formData.url" />
                </FormItem>
                <FormItem label="端口号" >
                    <Input v-model="formData.url" />
                </FormItem>
                <FormItem label="用户名" >
                    <Input v-model="formData.url" />
                </FormItem>
                <FormItem label="密码" >
                    <Input v-model="formData.url" />
                </FormItem>
                <FormItem label="Schema/数据库" >
                    <Input v-model="formData.url" />
                </FormItem>

                <FormItem label="编码" >
                    <Select v-model="formData.owner">
                        <Option value="jobs">Steven Paul Jobs</Option>
                        <Option value="ive">Sir Jonathan Paul Ive</Option>
                    </Select>
                </FormItem>

            </Form>
            <div class="demo-drawer-footer">
                <Button style="margin-right: 8px" @click="openDrawer = false">取消</Button>
                <Button type="primary" @click="openDrawer = false">保存</Button>
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
        data () {
            return {
                openDrawer: this.drawerVisible,
                styles: {
                    height: 'calc(100% - 55px)',
                    overflow: 'auto',
                    paddingBottom: '53px',
                    position: 'static'
                },
                formData: {
                    name: '',
                    url: '',
                    owner: '',
                    type: '',
                    approver: '',
                    date: '',
                    desc: ''
                },
            }
        },
        watch: {
            openDrawer(val) {
                this.$emit("update:drawerVisible", val)
            },
            drawerVisible(val) {
                this.openDrawer = val
            }
        }
    }
</script>
<style>
    .demo-drawer-footer{
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
