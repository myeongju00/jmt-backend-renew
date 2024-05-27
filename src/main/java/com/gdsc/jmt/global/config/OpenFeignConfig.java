package com.gdsc.jmt.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.gdsc.jmt")
public class OpenFeignConfig {
}
