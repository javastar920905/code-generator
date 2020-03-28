<template>
    <div>
        <Form ref="formValidate" :model="formValidate" :label-width="150">
            <FormItem label="表名">
                <Input v-model="formValidate.name" placeholder="person"></Input>
            </FormItem>
            <FormItem label="Java实体类名">
                <Input v-model="formValidate.mail" placeholder="Person"></Input>
            </FormItem>
            <FormItem label="主键(选填)">
                <Input v-model="formValidate.city" placeholder="primary key, such as id"></Input>

            </FormItem>
            <FormItem label="项目所在目录">
                <Input v-model="formValidate.name" placeholder="D:\workspace\example"></Input>
            </FormItem>
            <FormItem label="项目基础包名">
                <Input v-model="formValidate.mail" placeholder="com.company.project"></Input>
            </FormItem>
            <FormItem label="ftl模板所在目录">
                <Input v-model="formValidate.city" placeholder="D:\workspace\example"></Input>

            </FormItem>
            <Row>
                <Col span="11">
                    <FormItem label="controller包名">
                        <Input v-model="formValidate.city" placeholder="controller"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="service包名">
                        <Input v-model="formValidate.city" placeholder="service"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                    <FormItem label="实体类名包名">
                        <Input v-model="formValidate.city" placeholder="model"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="formValidate.city" placeholder="src/main/java"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                    <FormItem label="Mapper接口包名">
                        <Input v-model="formValidate.city" placeholder="mapper"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="formValidate.city" placeholder="src/main/java"></Input>
                    </FormItem>
                </Col>
            </Row>
            <FormItem label="通用mapper接口包名">
                <Input v-model="formValidate.city"></Input>
            </FormItem>
            <FormItem label="自定义接口名称（选填）">
                <Input v-model="formValidate.city"></Input>
            </FormItem>
            <Row>
                <Col span="11">
                    <FormItem label="映射XML文件包名">
                        <Input v-model="formValidate.city"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="formValidate.city" placeholder="service"></Input>
                    </FormItem>
                </Col>
            </Row>
            <FormItem>
                <div>
                    <span>生成文件的编码</span>
                    <Select v-model="formValidate.owner" style="width:150px;margin-left:10px">
                        <Option value="jobs">Steven Paul Jobs</Option>
                        <Option value="ive">Sir Jonathan Paul Ive</Option>
                    </Select>
                </div>
                <CheckboxGroup v-model="formValidate.interest">
                    <Checkbox label="Eat">使用Example</Checkbox>
                    <Checkbox label="Sleep">分页插件(暂时只支持MySQL和PostgreSQL)</Checkbox><br/>
                    <Checkbox label="Run">生成core包（BaseService，Result）</Checkbox>
                    <Checkbox label="Movie">生成实体域注释（来自表注释）</Checkbox>

                    <Checkbox label="Eat">覆盖原XML</Checkbox>
                    <Checkbox label="Sleep">生成toString/hashCode/equals方法</Checkbox><br/>
                    <Checkbox label="Run">使用Schema前缀</Checkbox>
                    <Checkbox label="Movie">select增加ForUpdate</Checkbox>

                    <Checkbox label="Eat">DAO使用@Repository注释</Checkbox>
                    <Checkbox label="Sleep">DAO方法抽出到公共父接口</Checkbox><br/>
                    <Checkbox label="Run">JSR310: Date and Time API</Checkbox>
                    <Checkbox label="Movie">生成JPA注释</Checkbox>
                    <Checkbox label="Run">使用实际的列名</Checkbox>
                    <Checkbox label="Movie">启用as别名查询</Checkbox>
                </CheckboxGroup>
            </FormItem>

            <FormItem>
                <Button type="primary" @click="handleSubmit('formValidate')">代码生成</Button>
                <Button @click="handleReset('formValidate')">保存配置</Button>
                <Button @click="handleReset('formValidate')">打开生成文件夹</Button>
            </FormItem>
        </Form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                formValidate: {
                    name: '',
                    mail: '',
                    city: '',
                    gender: '',
                    interest: [],
                    date: '',
                    time: '',
                    desc: ''
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$Message.success('Success!');
            },
            handleReset (name) {
                //this.$refs[name].resetFields();
            }
        }
    }
</script>