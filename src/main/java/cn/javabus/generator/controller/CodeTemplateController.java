package cn.javabus.generator.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ou.zhenxing on 2020-03-31.
 * <p>
 * todo 生成代码的模板管理 (增查)(删改到本地文件目录手动操作?)
 * todo 模板商城  (要能动态删改实体字段才行,用 json 配置字段?)
 * // TODO: 2020-03-31  内置变量列表
 */
@Api(tags = "代码模板 管理")
@RestController
@RequestMapping("/template")
public class CodeTemplateController {
}
