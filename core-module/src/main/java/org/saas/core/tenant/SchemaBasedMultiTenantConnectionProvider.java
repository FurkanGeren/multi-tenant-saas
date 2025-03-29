package org.saas.core.tenant;


import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();

    public void addDataSource(String tenantIdentifier, DataSource dataSource) {
        dataSources.put(tenantIdentifier, dataSource);
    }


    @Override
    public Connection getAnyConnection() throws SQLException {
        if (dataSources.isEmpty()) {
            throw new IllegalStateException("Hiçbir tenant için bağlantı yok.");
        }
        return dataSources.values().iterator().next().getConnection();
    }


    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        String schema = tenantIdentifier.toString();
        DataSource dataSource = dataSources.get(schema);
        if (dataSource == null) {
            throw new RuntimeException("Tenant için datasource bulunamadı: " + schema);
        }
        return dataSource.getConnection();
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