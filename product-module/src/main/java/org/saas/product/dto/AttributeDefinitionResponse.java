package org.saas.product.dto;

import java.util.List;

public record AttributeDefinitionResponse(
        Long id,
        String key,
        String label,
        String type,
        List<String> options
) {}