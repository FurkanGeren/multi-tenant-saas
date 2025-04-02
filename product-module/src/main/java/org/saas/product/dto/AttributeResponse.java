package org.saas.product.dto;

public record AttributeResponse(
        String key,
        String label,
        String value
) {}