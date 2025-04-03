package org.saas.tenant.controller;

import jakarta.validation.Valid;
import org.saas.core.domain.SubscriptionInfo;
import org.saas.tenant.dto.TenantFromKeyResponse;
import org.saas.tenant.dto.TenantRequest;
import org.saas.tenant.dto.TenantResolveRequest;
import org.saas.tenant.dto.TenantResponse;
import org.saas.tenant.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(@RequestBody @Valid TenantRequest request) {
        TenantResponse response = tenantService.createTenant(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        List<TenantResponse> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id) {
        TenantResponse tenant = tenantService.getTenantById(id);
        return ResponseEntity.ok(tenant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{tenantId}/subscription")
    public ResponseEntity<TenantResponse> updateTenantSubscription(
            @PathVariable Long tenantId,
            @RequestBody Long subscriptionPlanId) {

        TenantResponse tenantResponse = tenantService.updateTenantSubscription(tenantId, subscriptionPlanId);
        return ResponseEntity.ok(tenantResponse);
    }

    @GetMapping("/internal/{schema}/info")
    public ResponseEntity<SubscriptionInfo> getTenantInfo(@PathVariable("schema") String schema) {
        return ResponseEntity.ok(tenantService.getTenantInfoBySchema(schema));
    }

    @PostMapping("/resolve-tenant")
    public ResponseEntity<TenantFromKeyResponse> resolveTenant(@RequestBody TenantResolveRequest request) {
        return ResponseEntity.ok(tenantService.getTenantNameFromKey(request.accessKey()));

    }
}
