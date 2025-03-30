package org.saas.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@ComponentScan(basePackages = {
		"org.saas.core",
		"org.saas.tenant",
		"org.saas.user"

})
@EnableJpaAuditing
public class MainModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainModuleApplication.class, args);
	}

}
