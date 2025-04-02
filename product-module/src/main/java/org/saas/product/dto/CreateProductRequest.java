package org.saas.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        boolean active,
        List<AttributeValueRequest> attributes
) {}