<template>
    <div>
        <Form ref="formValidate" :model="formValidate" :label-width="100">
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
                    <!-- <Col span="2"></Col> -->
                    <Col span="11">
                        <FormItem label="service包名">
                            <Input v-model="formValidate.city" placeholder="service"></Input>
                        </FormItem>
                    </Col>
                </Row>
            <FormItem label="Gender" prop="gender">
                <RadioGroup v-model="formValidate.gender">
                    <Radio label="male">Male</Radio>
                    <Radio label="female">Female</Radio>
                </RadioGroup>
            </FormItem>
            <FormItem label="Hobby" prop="interest">
                <CheckboxGroup v-model="formValidate.interest">
                    <Checkbox label="Eat"></Checkbox>
                    <Checkbox label="Sleep"></Checkbox>
                    <Checkbox label="Run"></Checkbox>
                    <Checkbox label="Movie"></Checkbox>
                </CheckboxGroup>
            </FormItem>
            <FormItem label="Desc" prop="desc">
                <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
            </FormItem>
            <FormItem>
                <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
                <Button @click="handleReset('formValidate')" style="margin-left: 8px">Reset</Button>
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