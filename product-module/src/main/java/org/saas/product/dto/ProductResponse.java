package org.saas.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        boolean active,
        List<AttributeResponse> attributes
) {}