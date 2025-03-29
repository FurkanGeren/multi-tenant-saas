package org.saas.tenant.dto;

import java.time.LocalDateTime;

public record TenantResponse(
        Long id,
        String name,
        String databaseName,
        LocalDateTime createdAt
) {}