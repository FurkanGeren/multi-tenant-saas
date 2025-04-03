package org.saas.tenant.repository;

import org.saas.tenant.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByName(String name);

    Optional<Tenant> findByDatabaseName(String databaseName);

    boolean existsByName(String name);

    boolean existsByDatabaseName(String databaseName);

    Optional<Tenant> findByTenantKey(String key);
}
