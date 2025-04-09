package org.saas.tenant.multitenancy;

import jakarta.persistence.Entity;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TenantSchemaCreator {

    private static final String DB_URL = "jdbc:postgresql://localhost:5433/db_multi_tenant";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public void createSchema(String schemaName, Map<String, Object> baseSettings) {
        createSchemaIfNotExists(schemaName);
        createTables(schemaName, baseSettings);
    }

    private void createSchemaIfNotExists(String schemaName) {
        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
            System.out.println("✅ Şema oluşturuldu: " + schemaName);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Şema oluşturulurken hata oluştu: " + schemaName, e);
        }
    }

    private void createTables(String schemaName, Map<String, Object> baseSettings) {
        Map<String, Object> settings = new HashMap<>(baseSettings);
        settings.put("hibernate.default_schema", schemaName);
        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        settings.put("hibernate.connection.url", DB_URL);
        settings.put("hibernate.connection.username", DB_USERNAME);
        settings.put("hibernate.connection.password", DB_PASSWORD);
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        try {
            MetadataSources metadataSources = new MetadataSources(registry);

            // Tüm @Entity sınıflarını tara
            Reflections reflections = new Reflections("org.saas.core.domain");
            Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);

            for (Class<?> entity : entityClasses) {
                metadataSources.addAnnotatedClass(entity);
            }

            Metadata metadata = metadataSources.buildMetadata();

            SchemaManagementToolCoordinator.process(metadata, registry, settings, null);
            System.out.println("✅ Tablo oluşturma tamamlandı: " + schemaName);
            System.out.println("🛠️ [Manual] Hibernate şema oluşturuluyor.");

        } finally {
            // 🔥 Bellek sızıntısını önle
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}