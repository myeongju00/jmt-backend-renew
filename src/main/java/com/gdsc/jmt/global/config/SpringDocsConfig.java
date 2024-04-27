package com.gdsc.jmt.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SpringDocsConfig {

    @Value("${jmt.api.version}")
    private String API_VERSION;

    @Bean
    public OpenAPI JmtApi() {
        Info info = new Info()
                .title("JMT 프로젝트 리팩토링 API")
                .version(API_VERSION)
                .description("잘못된 부분이나 오류 발생 시 바로 말씀해주세요.")
                .contact(new Contact()
                        .name("GDSC DJU Peony")
                        .email("j51677767gmail.com"));

        // Security 스키마 설정
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        Server domainServer = new Server();
        domainServer.setUrl("https://api.jmt-matzip.dev");

        return new OpenAPI()
                .servers(Arrays.asList(localServer, domainServer))
                // Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("JWT", bearerAuth))
                // API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(addSecurityItem)
                .info(info);
    }
}
