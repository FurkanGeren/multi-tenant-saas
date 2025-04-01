package org.saas.audit.config;
import org.saas.core.tenant.MultiTenantJpaConfig;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import javax.sql.DataSource;


@Configuration
public class AuditHibernateConfig {


    private final JpaProperties jpaProperties;
    private final DataSource dataSource;

    public AuditHibernateConfig(JpaProperties jpaProperties, DataSource dataSource) {
        this.jpaProperties = jpaProperties;
        this.dataSource = dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        return MultiTenantJpaConfig.build(dataSource, jpaProperties, new String[]{"org.saas"});
    }
}


// Important for multi schema !!!!

