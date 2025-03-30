package org.saas.audit.config;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.saas.core.tenant.SchemaBasedMultiTenantConnectionProvider;
import org.saas.core.tenant.SchemaTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AuditHibernateConfig {


    private final JpaProperties jpaProperties;

    public AuditHibernateConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            @Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantIdentifierResolver
    ) {
        Map<String, Object> properties = new HashMap<>(jpaProperties.getProperties());

        properties.put("hibernate.multiTenancy", "SCHEMA");
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("org.saas");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new SchemaBasedMultiTenantConnectionProvider();
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new SchemaTenantIdentifierResolver();
    }
}
