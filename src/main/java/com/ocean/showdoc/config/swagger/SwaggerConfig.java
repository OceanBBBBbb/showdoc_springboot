package com.ocean.showdoc.config.swagger;

import com.google.common.base.Predicates;
import com.ocean.showdoc.common.GlobalConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@Profile({"local", "dev", "qa", "sit", "sandbox"})
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).groupName("showdoc-ocean-springboot").apiInfo(apiInfo())
        .select().apis(RequestHandlerSelectors.basePackage("com.ocean.showdoc"))
        .paths(Predicates.not(PathSelectors.regex("/error.*")))
        .build()
        .globalOperationParameters(setHeaders());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("cbi-mis-outer").contact(new Contact("group", "", ""))
        .version("1.0").build();
  }

  private List<Parameter> setHeaders() {
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(new ParameterBuilder().name(GlobalConst.USER_SESSION_KEY).description("用户登陆token")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build()
    );
    return parameters;
  }

}
