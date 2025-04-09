package org.saas.core.tenant;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Primary
public class SchemaBasedMultiTenantConnectionProvider implements MultiTenantConnectionProvider {

    private final HikariDataSource dataSource;

    public SchemaBasedMultiTenantConnectionProvider() {
        this.dataSource = new HikariDataSource();
        this.dataSource.setJdbcUrl("jdbc:postgresql://localhost:5433/db_multi_tenant");
        this.dataSource.setUsername("postgres");
        this.dataSource.setPassword("postgres");
        this.dataSource.setDriverClassName("org.postgresql.Driver");

        // 🔧 Pool ayarları
        this.dataSource.setMaximumPoolSize(30);
        this.dataSource.setMinimumIdle(1);
        this.dataSource.setIdleTimeout(15000);
        this.dataSource.setMaxLifetime(60000);
        this.dataSource.setPoolName("MainTenantPool");

        System.out.println("✅ [Spring] Ana DataSource başlatıldı. @" + this.hashCode());
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        String schema = tenantIdentifier.toString();
        Connection connection = getAnyConnection();
        connection.createStatement().execute("SET search_path TO " + schema);
        return connection;
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
        }
        throw new IllegalArgumentException("Unwrap yapılamıyor: " + unwrapType);
    }
}