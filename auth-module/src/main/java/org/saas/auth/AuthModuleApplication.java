package org.saas.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {
        "org.saas.auth",
        "org.saas.core"
})
@EnableFeignClients(basePackages = "org.saas.auth.client")
public class AuthModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthModuleApplication.class, args);
    }

}
