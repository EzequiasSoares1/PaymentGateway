package com.br.sysgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SysgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysgatewayApplication.class, args);
    }

}
