package me.chulgil.msa.money.aggreagation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restAPI() {
        return new Docket(springfox.documentation.spi.DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(springfox.documentation.builders.RequestHandlerSelectors.basePackage("me.chulgil.msa.money.aggreagation"))
                .paths(springfox.documentation.builders.PathSelectors.any())
                .build();
    }

    private springfox.documentation.service.ApiInfo apiInfo() {
        return new springfox.documentation.builders.ApiInfoBuilder()
                .build();
    }
}
