package org.saas.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.saas")
public class CoreModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreModuleApplication.class, args);
    }

}
