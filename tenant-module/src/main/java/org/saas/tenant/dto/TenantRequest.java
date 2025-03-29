package org.saas.tenant.dto;

import jakarta.validation.constraints.NotBlank;

public record TenantRequest(
        @NotBlank(message = "Tenant adı boş olamaz")
        String name,

        @NotBlank(message = "Veritabanı adı boş olamaz")
        String databaseName,

        Long subscriptionPlanId
) {}