package org.saas.product.dto;

import java.util.List;

public record CreateAttributeRequest(
        String key,
        String label,
        String type,
        List<String> options
) {}