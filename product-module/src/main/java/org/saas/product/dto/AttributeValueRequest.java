package org.saas.product.dto;

public record AttributeValueRequest(
        Long definitionId,
        String value
) {}