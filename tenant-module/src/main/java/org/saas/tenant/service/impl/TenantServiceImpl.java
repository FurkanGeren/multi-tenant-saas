package org.saas.tenant.service.impl;

import org.saas.core.domain.SubscriptionInfo;
import org.saas.core.exception.BusinessException;
import org.saas.tenant.multitenancy.TenantSchemaCreator;
import org.saas.tenant.dto.TenantRequest;
import org.saas.tenant.dto.TenantResponse;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.entity.Tenant;
import org.saas.core.tenant.TenantContext;
import org.saas.tenant.repository.SubscriptionPlanRepository;
import org.saas.tenant.repository.TenantRepository;
import org.saas.tenant.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantSchemaCreator tenantSchemaCreator;
    private final EntityManager entityManager;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantSchemaCreator tenantSchemaCreator, EntityManager entityManager, SubscriptionPlanRepository subscriptionPlanRepository) {
        this.tenantRepository = tenantRepository;
        this.tenantSchemaCreator = tenantSchemaCreator;
        this.entityManager = entityManager;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
   // @Transactional
    public TenantResponse createTenant(TenantRequest request) {
        // 1. Tenant adı ve veritabanı adı kontrolü
        if (tenantRepository.existsByName(request.name())) {
            throw new BusinessException("Bu tenant adı zaten kullanılıyor.");
        }

        if (tenantRepository.existsByDatabaseName(request.databaseName())) {
            throw new BusinessException("Bu veritabanı adı zaten kullanılıyor.");
        }

        // 2. Abonelik planını al
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(request.subscriptionPlanId())
                .orElseThrow(() -> new BusinessException("Geçersiz abonelik planı."));

        // 3. Şema oluştur
  //      entityManager.createNativeQuery("CREATE SCHEMA IF NOT EXISTS " + request.databaseName())
   //             .executeUpdate();

        // 4. Tenant'ı oluştur
        Tenant tenant = new Tenant(request.name(), request.databaseName());
        tenant.setSubscriptionPlan(subscriptionPlan); // Abonelik planını set et

        System.out.println("Tenant kaydediliyor...");
        Tenant saved = tenantRepository.save(tenant);
        System.out.println("Tenant kaydedildi.");

        // 5. Context'e geçici olarak tenant schema'yı ata
        TenantContext.setTenantSchema(request.databaseName());

        // 6. Tabloları oluştur
        Map<String, Object> settings = Map.of(
                "hibernate.connection.driver_class", "org.postgresql.Driver",
                "hibernate.connection.url", "jdbc:postgresql://localhost:5433/db_multi_tenant",
                "hibernate.connection.username", "postgres",
                "hibernate.connection.password", "postgres",
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"
        );

        tenantSchemaCreator.createSchema(request.databaseName(), new HashMap<>(settings));

        // 7. TenantContext temizle
        TenantContext.clear();
        System.out.println("Tenant oluşturuldu: " + saved.getName());
        // 8. TenantResponse döndür
        return new TenantResponse(
                saved.getId(),
                saved.getName(),
                saved.getDatabaseName(),
                saved.getCreatedAt()
        );
    }

    @Transactional
    @Override
    public TenantResponse updateTenantSubscription(Long tenantId, Long subscriptionPlanId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new BusinessException("Tenant bulunamadı"));

        SubscriptionPlan newPlan = subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new BusinessException("Abonelik planı bulunamadı"));

        tenant.setSubscriptionPlan(newPlan);
        tenantRepository.save(tenant);

        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getDatabaseName(),
                tenant.getCreatedAt()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Tenant bulunamadı."));
        return mapToResponse(tenant);
    }

    @Override
    @Transactional
    public void deleteTenant(Long id) {
        if (!tenantRepository.existsById(id)) {
            throw new BusinessException("Silinecek tenant bulunamadı.");
        }
        tenantRepository.deleteById(id);
    }

    @Override
    public SubscriptionInfo getTenantInfoBySchema(String schemaName) {
        Tenant tenant = tenantRepository.findByDatabaseName(schemaName)
                .orElseThrow(() -> new RuntimeException("Tenant bulunamadı"));

        return new SubscriptionInfo(
                tenant.getName(),
                tenant.getDatabaseName(),
                tenant.getSubscriptionPlan().getMaxUsers()
        );
    }

    private TenantResponse mapToResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getDatabaseName(),
                tenant.getCreatedAt()
        );
    }
}