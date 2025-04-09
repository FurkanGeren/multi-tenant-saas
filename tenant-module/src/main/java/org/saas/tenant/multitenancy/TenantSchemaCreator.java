package org.saas.tenant.multitenancy;

import jakarta.persistence.Entity;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator;
import org.reflections.Reflections;
import org.saas.core.domain.*;
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



    public void createSchema(String schemaName, Map<String, Object> baseSettings) {

        try {
            System.out.println("Şema oluşturuluyor...");

            // ŞEMA OLUŞTURULUYOR: Ayrı bir connection ile CREATE SCHEMA
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5433/db_multi_tenant",
                    "postgres",
                    "postgres"
            );
            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
            statement.close();
            connection.close();

            // TABLOLARI OLUŞTUR: Hibernate metadata ile
            Map<String, Object> settings = new HashMap<>(baseSettings);
            settings.put("hibernate.default_schema", schemaName);
            settings.put("hibernate.hbm2ddl.auto", "update"); // create değil, update olsun
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5433/db_multi_tenant");
            settings.put("hibernate.connection.username", "postgres");
            settings.put("hibernate.connection.password", "postgres");

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            MetadataSources metadataSources = new MetadataSources(registry);


            Reflections reflections = new Reflections("org.saas.core.domain"); // domain paketine göre değiştir
            Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);

            for (Class<?> entity : entityClasses) {
                metadataSources.addAnnotatedClass(entity);
            }



            Metadata metadata = metadataSources.buildMetadata();

            SchemaManagementToolCoordinator.process(
                    metadata,
                    registry,
                    settings,
                    null
            );
            System.out.println("Şema oluşturma tamamlandı.");
        } catch (SQLException e) {
            throw new RuntimeException("Şema oluşturulurken hata oluştu: " + schemaName, e);
        }
    }
}
