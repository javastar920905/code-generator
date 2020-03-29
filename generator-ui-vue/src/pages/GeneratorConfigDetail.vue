<template>
    <div>
        <Form ref="generateConfig" :model="generateConfig" :label-width="150">

            <FormItem>
                <Button type="primary" @click="handleSubmit('generateConfig')">代码生成</Button>
                <Button @click="saveConfig">保存配置</Button>
                <Button @click="handleReset('generateConfig')">打开生成文件夹</Button>
            </FormItem>

            <FormItem label="表名">
                <Input v-model="generateConfig.tableName" placeholder="person"></Input>
            </FormItem>
            <FormItem label="Java实体类名">
                <Input v-model="generateConfig.domainObjectName" placeholder="Person"></Input>
            </FormItem>
            <FormItem label="主键(选填)">
                <Input v-model="generateConfig.generateKeys" placeholder="primary key, such as id"></Input>

            </FormItem>
            <FormItem label="项目所在目录">
                <Input v-model="generateConfig.projectFolder" placeholder="D:\workspace\example"></Input>
            </FormItem>
            <FormItem label="项目基础包名">
                <Input v-model="generateConfig.basePackage" placeholder="com.company.project"></Input>
            </FormItem>
            <FormItem label="ftl模板所在目录">
                <Input v-model="generateConfig.ftlTemplateFolder" placeholder="D:\workspace\example"></Input>

            </FormItem>
            <Row>
                <Col span="11">
                    <FormItem label="controller包名">
                        <Input v-model="generateConfig.controller" placeholder="controller"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="service包名">
                        <Input v-model="generateConfig.service" placeholder="service"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                    <FormItem label="实体类名包名">
                        <Input v-model="generateConfig.modelPackage" placeholder="model"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="generateConfig.modelPackageTargetFolder" placeholder="src/main/java"></Input>
                    </FormItem>
                </Col>
            </Row>
            <Row>
                <Col span="11">
                    <FormItem label="Mapper接口包名">
                        <Input v-model="generateConfig.daoPackage" placeholder="mapper"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="generateConfig.daoTargetFolder" placeholder="src/main/java"></Input>
                    </FormItem>
                </Col>
            </Row>
            <FormItem label="通用mapper接口包名">
                <Input v-model="generateConfig.tkCommonMapper"></Input>
            </FormItem>
            <FormItem label="自定义接口名称（选填）">
                <Input v-model="generateConfig.mapperName"></Input>
            </FormItem>
            <Row>
                <Col span="11">
                    <FormItem label="映射XML文件包名">
                        <Input v-model="generateConfig.mappingXMLPackage"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="generateConfig.mappingXMLTargetFolder" placeholder="service"></Input>
                    </FormItem>
                </Col>
            </Row>
            <FormItem>
                <div>
                    <span>生成文件的编码</span>
                    <Select v-model="generateConfig.encoding" style="width:150px;margin-left:10px">
                        <Option value="UTF-8">UTF-8</Option>
                    </Select>
                </div>
                <Checkbox v-model="generateConfig.useExample">使用Example</Checkbox>
                <Checkbox v-model="generateConfig.offsetLimit">分页插件(暂时只支持MySQL和PostgreSQL)</Checkbox>
                <br/>
                <Checkbox v-model="generateConfig.corePackageFlag">生成core包（BaseService，Result）</Checkbox>
                <Checkbox v-model="generateConfig.comment">生成实体域注释（来自表注释）</Checkbox>

                <Checkbox v-model="generateConfig.overrideXML">覆盖原XML</Checkbox>
                <Checkbox v-model="generateConfig.needToStringHashcodeEquals">生成toString/hashCode/equals方法</Checkbox>
                <br/>
                <Checkbox v-model="generateConfig.useSchemaPrefix">使用Schema前缀</Checkbox>
                <Checkbox v-model="generateConfig.needForUpdate">select增加ForUpdate</Checkbox>

                <Checkbox v-model="generateConfig.annotationDAO">DAO使用@Repository注释</Checkbox>
                <Checkbox v-model="generateConfig.aa">DAO方法抽出到公共父接口</Checkbox>
                <br/>
                <Checkbox v-model="generateConfig.jsr310Support">JSR310: Date and Time API</Checkbox>
                <Checkbox v-model="generateConfig.movie">生成JPA注释</Checkbox>
                <Checkbox v-model="generateConfig.run">使用实际的列名</Checkbox>
                <Checkbox v-model="generateConfig.useTableNameAlias">启用as别名查询</Checkbox>
            </FormItem>

        </Form>
    </div>
</template>
<script>
    export default {
        props: {
            parentSelectedTableName: {//接收父组件传值
                type: String,
                default: ""
            }
        },
        data() {
            return {
                generateConfig: {
                    name: "dat",//要保存的配置名称
                    tableName: this.innerSelectedTableName,//选中的数据表名称
                    domainObjectName: "",//实体类名称
                    projectFolder: "/Users/axing/Documents/java/workspace/generator",
                    modelPackage: "cn.javabus.generator.test.model",
                    modelPackageTargetFolder: "src/main/java",
                    daoPackage: "cn.javabus.generator.test.dao",
                    daoTargetFolder: "src/main/java",
                    mapperName: "LifeProductMappper",
                    mappingXMLPackage: "cn.javabus.generator.test.xmlSqlMap",
                    mappingXMLTargetFolder: "src/main/java",


                    offsetLimit: "false",
                    comment: true,
                    overrideXML: true,
                    needToStringHashcodeEquals: "false",
                    needForUpdate: "false",
                    annotationDAO: true,
                    annotation: "false",
                    useActualColumnNames: "false",
                    useExample: "false",
                    generateKeys: "",
                    encoding: "UTF-8",
                    useTableNameAlias: "false",
                    useDAOExtendStyle: "false",
                    useSchemaPrefix: "false",
                    jsr310Support: "false",
                    basePackage: "cn.javabus.generator",
                    controller: "cn.javabus.generator.test.ctrl",
                    service: "cn.javabus.generator.test.serve",
                    tkCommonMapper: "tk.mybatis.mapper.common.Mapper",
                    ftlTemplateFolder: "/Users/axing/Documents/java/workspace/generator/src/main/resources/generator/template",
                    corePackageFlag: "false",
                }
            }
        },
        mounted(){
            //监听事件
            this.$eventHub.$on('applyGeneratorConfig', (val)=>{
                console.log('广播传过来的值是'+val);
                //加载数据
                this.axios.get('/generator/getConfig?generatorConfigName='+val)
                    .then((response) => {
                        // handle success
                        this.generateConfig = response.data.data;
                        this.okMsg("应用成功!")
                    })
                    .catch((error) => {
                        this.errMsg(error)
                    })
            } )
        },
        watch: {
            parentSelectedTableName(val){//监听父页面传递过来的值
                this.generateConfig.tableName=val;
            },
            "generateConfig.tableName":function(tbName) {
                if (tbName != null && tbName.trim() != '') {
                    //下划线改为驼峰命名
                    this.generateConfig.domainObjectName = tbName.replace(/\_(\w)/g, function (all, letter) {
                        return letter.toUpperCase();
                    });
                }
            }
        },
        methods: {
            saveConfig() {
                this.axios.post('/generator/saveConfig', {
                    ...this.generateConfig
                }).then(response => {
                    this.msg(response)
                }).catch(error => {
                    this.errMsg(error)
                })
            },
            handleSubmit(name) {
                this.axios.post('/generator/generateCode', {
                    ...this.generateConfig
                }).then(response => {
                    this.msg(response)
                }).catch(error => {
                    this.errMsg(error)
                })
            },
            handleReset(name) {
                //this.$refs[name].resetFields();
            }
        }
    }
</script>
<style>
    .ivu-form-item{
        margin-bottom: 2px !important;
    }
</style>