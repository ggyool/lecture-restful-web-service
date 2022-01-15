package org.ggyool.onlinelecturerestfulwebservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    // 연락처 정보를 위한 객체
    private static final Contact DEFAULT_CONTACT = new Contact(
            "ggyool", "http://www.ggyool.co.kr", "ggyool@email.com"
    );

    // API info 값을 위한 객체
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "API Title",
            "API Description",
            "1.0",
            "termsOfServiceUrl",
            DEFAULT_CONTACT,
            "license",
            "licenseUrl",
            new ArrayList<>()
    );

    // 문서 타입
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMERS = new HashSet<>(
            Arrays.asList("application/json", "application/xml")
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMERS)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMERS);
    }
}
