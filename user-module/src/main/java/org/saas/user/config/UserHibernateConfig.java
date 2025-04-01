package org.saas.user.config;
import org.saas.core.tenant.MultiTenantJpaConfig;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import javax.sql.DataSource;

@Configuration
public class UserHibernateConfig {

    private final JpaProperties jpaProperties;
    private final DataSource dataSource;

    public UserHibernateConfig(JpaProperties jpaProperties, DataSource dataSource) {
        this.jpaProperties = jpaProperties;
        this.dataSource = dataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return MultiTenantJpaConfig.build(dataSource, jpaProperties, new String[]{"org.saas"});
    }
}