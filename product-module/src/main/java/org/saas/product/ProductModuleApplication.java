package org.saas.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"org.saas.core",
		"org.saas.product"
})
@EnableJpaAuditing
@ComponentScan(basePackages = {"org.saas.core", "org.saas.product"})
@EnableJpaRepositories
public class ProductModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductModuleApplication.class, args);
	}

}
