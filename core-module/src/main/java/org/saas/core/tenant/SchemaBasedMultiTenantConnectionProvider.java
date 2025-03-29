package org.saas.core.tenant;


import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// core-module/src/main/java/org/saas/core/tenant/SchemaBasedMultiTenantConnectionProvider.java
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();


    public SchemaBasedMultiTenantConnectionProvider() {
        createAndAddDataSourceForTenant("public");
    }

    public void addDataSource(String tenantIdentifier, DataSource dataSource) {
        dataSources.put(tenantIdentifier, dataSource);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        if (dataSources.isEmpty()) throw new IllegalStateException("Hiçbir tenant için bağlantı yok.");
        return dataSources.values().iterator().next().getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        String tenantId = tenantIdentifier.toString();

        DataSource dataSource = dataSources.get(tenantId);
        if (dataSource == null) {
            // Burada yeni datasource oluştur
            dataSource = createAndAddDataSourceForTenant(tenantId);
        }

        return dataSource.getConnection();
    }

    private DataSource createAndAddDataSourceForTenant(String schema) {
        // Her tenant için ayrı bir schema ama aynı DB
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5433/db_multi_tenant");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName("org.postgresql.Driver");

        // Tenant'a özel schema'yı set etmek için PostgreSQL'in bu özelliğini kullan
        dataSource.setConnectionInitSql("SET search_path TO " + schema);

        dataSources.put(schema, dataSource);
        return dataSource;
    }


    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (isUnwrappableAs(unwrapType)) {
            return unwrapType.cast(this);
        } else {
            throw new IllegalArgumentException("Unwrap yapılamıyor: " + unwrapType);
        }
    }
}