<template>
    <div>
        <Form ref="generateConfig" :model="generateConfig" :label-width="150">
            <FormItem label="表名">
                <Input v-model="generateConfig.name" placeholder="person"></Input>
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
                <CheckboxGroup v-model="generateConfig.interest">
                    <Checkbox label="useExample">使用Example</Checkbox>
                    <Checkbox label="offsetLimit">分页插件(暂时只支持MySQL和PostgreSQL)</Checkbox>
                    <br/>
                    <Checkbox label="corePackageFlag">生成core包（BaseService，Result）</Checkbox>
                    <Checkbox label="comment">生成实体域注释（来自表注释）</Checkbox>

                    <Checkbox label="overrideXML">覆盖原XML</Checkbox>
                    <Checkbox label="needToStringHashcodeEquals">生成toString/hashCode/equals方法</Checkbox>
                    <br/>
                    <Checkbox label="useSchemaPrefix">使用Schema前缀</Checkbox>
                    <Checkbox label="needForUpdate">select增加ForUpdate</Checkbox>

                    <Checkbox label="annotationDAO">DAO使用@Repository注释</Checkbox>
                    <Checkbox label="">DAO方法抽出到公共父接口</Checkbox>
                    <br/>
                    <Checkbox label="jsr310Support">JSR310: Date and Time API</Checkbox>
                    <Checkbox label="Movie">生成JPA注释</Checkbox>
                    <Checkbox label="Run">使用实际的列名</Checkbox>
                    <Checkbox label="useTableNameAlias">启用as别名查询</Checkbox>
                </CheckboxGroup>
            </FormItem>

            <FormItem>
                <Button type="primary" @click="handleSubmit('generateConfig')">代码生成</Button>
                <Button @click="handleReset('generateConfig')">保存配置</Button>
                <Button @click="handleReset('generateConfig')">打开生成文件夹</Button>
            </FormItem>
        </Form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                generateConfig: {
                    name: "",
                    connectorJarPath: "",
                    projectFolder: "",
                    modelPackage: "",
                    modelPackageTargetFolder: "",
                    daoPackage: "",
                    daoTargetFolder: "",
                    mapperName: "",
                    mappingXMLPackage: "",
                    mappingXMLTargetFolder: "",
                    tableName: "",
                    domainObjectName: "",
                    offsetLimit: "",
                    comment: "",
                    overrideXML: "",
                    needToStringHashcodeEquals: "",
                    needForUpdate: "",
                    annotationDAO: "",
                    annotation: "",
                    useActualColumnNames: "",
                    useExample: "",
                    generateKeys: "",
                    encoding: "",
                    useTableNameAlias: "",
                    useDAOExtendStyle: "",
                    useSchemaPrefix: "",
                    jsr310Support: "",
                    basePackage: "",
                    controller: "",
                    service: "",
                    tkCommonMapper: "",
                    ftlTemplateFolder: "",
                    corePackageFlag: "",
                }
            }
        },
        methods: {
            handleSubmit(name) {
                this.$Message.success('Success!');
            },
            handleReset(name) {
                //this.$refs[name].resetFields();
            }
        }
    }
</script>