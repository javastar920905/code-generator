package cn.javabus.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ou.zhenxing on 2020-03-29.
 * http://localhost:8080/swagger-ui.html
 * <p>
 * 在Security中的配置
 * 如果我们的Spring Boot项目中集成了Spring Security，那么如果不做额外配置，Swagger2文档可能会被拦截，此时只需要在Spring Security的配置类中重写configure方法，添加如下过滤即可：
 * @Override public void configure(WebSecurity web) throws Exception {
 * web.ignoring()
 * .antMatchers("/swagger-ui.html")
 * .antMatchers("/v2/**")
 * .antMatchers("/swagger-resources/**");
 * }
 *
 *
 * @ApiImplicitParams({ *             @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "李四"),
 * *             @ApiImplicitParam(name = "address", value = "用户地址", defaultValue = "深圳", required = true)
 * *     }
 * *     )
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.javabus.generator.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("代码生成器接口文档，基于 swagger")
                        .description("方便前端进行接口对接")
                        .version("9.0")
                        .contact(new Contact("javabus.cn", "http://javabus,cn", "javastar920905@163.com"))
                        //.license("The Apache License")
                        //.licenseUrl("http://javabus.cn")
                        .build());
    }
}
