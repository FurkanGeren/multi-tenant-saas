package org.saas.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {
        "org.saas.core",
        "org.saas.auth"
})
@EnableJpaAuditing
@EnableJpaRepositories
@EnableFeignClients(basePackages = "org.saas.auth.client")
public class AuthModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthModuleApplication.class, args);
    }

}
