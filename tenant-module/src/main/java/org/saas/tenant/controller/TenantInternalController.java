package org.saas.tenant.controller;

import org.saas.tenant.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internal/tenants")
public class TenantInternalController {

    private final TenantService tenantService;

    public TenantInternalController(TenantService tenantService) {
        this.tenantService = tenantService;
    }


    @GetMapping("/schemas")
    public ResponseEntity<List<String>> getSchemas() {
        return ResponseEntity.ok(tenantService.getAllSchemas());
    }
}
