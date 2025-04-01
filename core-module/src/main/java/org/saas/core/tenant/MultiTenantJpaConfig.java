package org.saas.core.tenant;

import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class MultiTenantJpaConfig {

    public static LocalContainerEntityManagerFactoryBean build(
            DataSource dataSource,
            JpaProperties jpaProperties,
            String[] basePackages
    ) {
        Map<String, Object> properties = new HashMap<>(jpaProperties.getProperties());

        properties.put("hibernate.multiTenancy", "SCHEMA");
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, new SchemaBasedMultiTenantConnectionProvider());
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, new SchemaTenantIdentifierResolver());
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan(basePackages);
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    public static MultiTenantConnectionProvider multiTenantConnectionProvider() {
        return new SchemaBasedMultiTenantConnectionProvider();
    }

    @Bean
    public static CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new SchemaTenantIdentifierResolver();
    }
}
