
package com.timeOrganizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    /*
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("My API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        Arrays.asList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Server Error")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(400)
                                        .message("Bad Request")
                                        .responseModel(new ModelRef("Error"))
                                        .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My API")
                .description("A sample API for demonstration purposes")
                .version("1.0")
                .termsOfServiceUrl("http://www.example.com/terms")
                .contact(new Contact("John Doe", "http://www.example.com", "johndoe@example.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }
     */
}


