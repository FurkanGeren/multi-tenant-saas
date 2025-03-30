package org.saas.tenant.controller;

import org.saas.core.domain.enums.ModuleType;
import org.saas.tenant.entity.SubscriptionPlan;
import org.saas.tenant.service.TenantService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/plan")
public class SubscriptionPlanInternalController {

    private final TenantService tenantService;

    public SubscriptionPlanInternalController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/{tenantId}")
    public boolean isModuleEnabled(
            @PathVariable("tenantId") String tenantId,
            @RequestParam("module") String moduleType
    ) {
        ModuleType convertToModuleType = ModuleType.valueOf(moduleType);
        System.out.println(convertToModuleType);
        SubscriptionPlan plan = tenantService.getPlanForTenant(tenantId);
        return switch (convertToModuleType) {
            case USER, TENANT, AUTH -> true;
            case REPORTING -> plan.isHasAdvancedReporting();
            case AUDIT -> plan.isHasFullAuditLogging();
            case INTEGRATION -> plan.isHasCustomIntegrations();
        };
    }
}
