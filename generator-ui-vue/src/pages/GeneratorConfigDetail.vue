<template>
    <div>
        <Form ref="generateConfig" :model="generateConfig" :label-width="180" style="font-weight: bold">

            <FormItem :label="generateConfig.selectedDatabaseConfig.name">
                <Button type="primary" @click="handleSubmit('generateConfig')">代码生成</Button>
                <Button @click="saveConfig">保存配置</Button>
                <Input style="width: 300px" v-model="generateConfig.name" placeholder="请输入要保存的配置名称 (名称重复会覆盖)"></Input>
                <!--<Button @click="handleReset('generateConfig')">打开生成文件夹-web 无法实现该功能</Button>-->
            </FormItem>

            <Row>
                <Col span="7">
                    <FormItem label="表名">
                        <Input v-model="generateConfig.tableName" placeholder="person"></Input>
                    </FormItem>
                </Col>
                <Col span="7">
                    <FormItem label="Java实体类名">
                        <Input v-model="generateConfig.domainObjectName" placeholder="Person"></Input>
                    </FormItem>
                </Col>
                <Col span="7">
                    <FormItem label="主键(选填)">
                        <Input v-model="generateConfig.generateKeys" placeholder="primary key, such as id"></Input>
                    </FormItem>
                </Col>
            </Row>
            <FormItem label="项目所在目录">
                <Input  v-model="generateConfig.projectFolder" style="width:50%; display: inline" placeholder="D:\workspace\example"></Input>
                <!--<Upload :before-upload="chooseDir"  action="" >-->
                <!--遗憾,前端获取不到路径-->
                <!--<Button icon="ios-cloud-upload-outline">选择</Button>-->
                <!--</Upload>-->
            </FormItem>
            <FormItem label="项目基础包名">
                <Input v-model="generateConfig.basePackage" placeholder="com.company.project"></Input>
            </FormItem>
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
            <Row>
                <Col span="11">
                    <FormItem label="dao接口包名">
                        <Input v-model="generateConfig.daoPackage" placeholder="mapper"></Input>
                    </FormItem>
                </Col>
                <Col span="11">
                    <FormItem label="存放目录">
                        <Input v-model="generateConfig.daoTargetFolder" placeholder="src/main/java"></Input>
                    </FormItem>
                </Col>

            </Row>
            <FormItem label="自定义dao接口名称（选填）">
                <Input v-model="generateConfig.mapperName" placeholder="PersonMapper"></Input>
            </FormItem>
            <FormItem label="通用mapper接口包名" v-if="generateConfig.userCommonMapper">
                <Input v-model="generateConfig.tkCommonMapper"></Input>
            </FormItem>
            <FormItem label="ftl模板所在目录" v-if="generateConfig.genCtrl">
                <Input v-model="generateConfig.ftlTemplateFolder" placeholder="D:\workspace\example"></Input>

            </FormItem>
            <Row v-if="generateConfig.genCtrl">
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
            <FormItem>
                <!--<div>-->
                    <!--<span>生成文件的编码</span>-->
                    <!--<Select v-model="generateConfig.encoding" style="width:150px;margin-left:10px">-->
                        <!--<Option value="UTF-8">UTF-8</Option>-->
                    <!--</Select>-->
                <!--</div>-->
                <!--实体类-->
                <Checkbox v-model="generateConfig.comment">生成实体域注释（来自表注释）</Checkbox>
                <Checkbox v-model="generateConfig.useExample">使用Example</Checkbox>
                <Checkbox v-model="generateConfig.useLombok" disabled>使用Lombok</Checkbox>
                <Checkbox v-model="generateConfig.needToStringHashcodeEquals">生成toString/hashCode/equals方法</Checkbox>
                <br/>
                <!--sqlmap xml-->
                <Checkbox v-model="generateConfig.overrideXML">覆盖原XML</Checkbox>
                <Checkbox v-model="generateConfig.useSchemaPrefix">使用Schema前缀</Checkbox>
                <Checkbox v-model="generateConfig.needForUpdate">select增加ForUpdate</Checkbox>
                <Checkbox v-model="generateConfig.offsetLimit">分页插件(暂时只支持MySQL和PostgreSQL)</Checkbox>
                <br/>
                <!--注解相关-->
                <Checkbox v-model="generateConfig.jsr310Support">JSR310: Date and Time API</Checkbox>
                <Checkbox v-model="generateConfig.movie">生成JPA注释</Checkbox>
                <Checkbox v-model="generateConfig.run">使用实际的列名</Checkbox>
                <Checkbox v-model="generateConfig.useTableNameAlias">启用as别名查询</Checkbox>
                <br/>
                <!--dao 接口-->
                <Checkbox v-model="generateConfig.userCommonMapper">DAO使用通用 mapper</Checkbox>
                <Checkbox v-model="generateConfig.annotationDAO">DAO使用@Repository注释</Checkbox>
                <Checkbox v-model="generateConfig.aa">DAO方法抽出到公共父接口</Checkbox>

                <br/>
                <!--controller 和 service-->
                <Checkbox v-model="generateConfig.genCtrl">生成 controller 和 service</Checkbox>
                <Checkbox v-model="generateConfig.corePackageFlag" disabled>生成core包（BaseService，Result）</Checkbox>
                <Checkbox v-model="generateConfig.useSwagger" disabled>VO层使用swagger</Checkbox>
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
                    selectedDatabaseConfig:{name:'请选择数据库'},//持有数据库连接
                    name: "",//要保存的配置名称
                    tableName: this.innerSelectedTableName,//选中的数据表名称
                    domainObjectName: "",//实体类名称
                    projectFolder: "/Users/axing/Documents/java/workspace/generator",
                    modelPackage: "cn.javabus.generator.test.model",
                    modelPackageTargetFolder: "src/main/java",
                    daoPackage: "cn.javabus.generator.test.dao",
                    daoTargetFolder: "src/main/java",
                    mapperName: "",//自定义dao接口名称
                    mappingXMLPackage: "cn.javabus.generator.test.xmlSqlMap",
                    mappingXMLTargetFolder: "src/main/java",


                    offsetLimit: false,
                    comment: true,
                    overrideXML: true,
                    needToStringHashcodeEquals: false,
                    needForUpdate: false,
                    annotationDAO: false,
                    annotation: false,
                    useActualColumnNames: false,
                    useExample: true,
                    generateKeys: "",
                    encoding: "UTF-8",
                    useTableNameAlias: false,
                    useDAOExtendStyle: false,
                    useSchemaPrefix: false,
                    jsr310Support: false,
                    userCommonMapper:false,
                    tkCommonMapper: "tk.mybatis.mapper.common.Mapper",
                    ftlTemplateFolder: "/Users/axing/Documents/java/workspace/generator/src/main/resources/generator/template",
                    corePackageFlag: false,
                    genCtrl:false,
                    basePackage: "cn.javabus.generator",
                    controller: "cn.javabus.generator.test.ctrl",
                    service: "cn.javabus.generator.test.serve",
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
            } );

            //监听左侧导航栏 数据库选中事件
            this.$eventHub.$on('selectedDBConifg', (dbConfig)=>{
                 console.log('广播传过来的值是');
                 console.log(dbConfig)
                if (dbConfig){
                    this.generateConfig.selectedDatabaseConfig=dbConfig;
                    this.generateConfig.name=dbConfig.name+"-"+this.generateConfig.basePackage
                }

            } );

        },
        watch: {
            parentSelectedTableName(val){//监听父页面传递过来的值
                this.generateConfig.tableName=val;
            },
            "generateConfig.tableName":function(tbName) {
                if (tbName != null && tbName.trim() != '') {
                    //首字母大写
                    tbName=tbName.charAt(0).toUpperCase() + tbName.slice(1)
                    //下划线改为驼峰命名
                    this.generateConfig.domainObjectName = tbName.replace(/\_(\w)/g, function (all, letter) {
                        console.log(letter)
                        return letter.toUpperCase();
                    });
                }
            }
        },
        methods: {
            saveConfig() {
                if (this.generateConfig.name==null||this.generateConfig.name==""){
                    this.infoMsg("请先填写要保存的配置名称");
                    return;
                }
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
<style >
    .ivu-form-item{
        margin-bottom: 2px !important;
    }
    /*.ivu-input{*/
        /*width: 84% !important;*/
    /*}*/
</style>