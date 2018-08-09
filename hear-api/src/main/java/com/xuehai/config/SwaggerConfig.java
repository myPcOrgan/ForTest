package com.xuehai.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27 0027.
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {

    public static Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xuehai.business"))
                .paths(PathSelectors.any())
                .build();
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学海听说3.0服务接口")
                .description("`user-Agent`头传输设备、客户端相关的信息，如：\r\n" +
                        "> user-Agent: 应用名称或包名/应用版本号 (设备型号; android|ios; 系统版本号; 5.0设备串号|U)\n" +
                        "> U表示未定义或未知\n" +
                        "> 比如：\n" +
                        "> user-Agent: com.xhls.acstu/1.0 (P350; android; 5.0; 设备串号)\n" +
                        "\n" +
                        "`Authorization`头传输用户认证的token，如：\n" +
                        "> Authorization: Bearer 4086c8d4-8ca4-42d3-a900-c9e7a5e8329d")
                .version("1.0")
                .build();
    }


    @Configuration
    @ConditionalOnProperty(name = "ddd.jwt.enabled", havingValue = "false")
    protected static class SwaggerWithoutAuthorization {

        @Bean
        public Docket restApi() {
            Docket api = createRestApi();
            return api;
        }
    }


    @Configuration
    @ConditionalOnProperty(name = "ddd.jwt.enabled", havingValue = "true")
    protected static class SwaggerWithAuthorization {

        @Bean
        public Docket restApi() {
            Docket api = createRestApi();
            List<Parameter> arrayList = new ArrayList<>();
            Parameter parameter = new ParameterBuilder()
                    .name("Authorization")
                    .description("token")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .defaultValue("Bearer")
                    .build();
            arrayList.add(parameter);
            api.globalOperationParameters(arrayList);
            return api;
        }
    }
}


