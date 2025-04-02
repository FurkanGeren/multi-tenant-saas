package org.saas.product.service;

import org.saas.product.dto.AttributeDefinitionResponse;
import org.saas.product.dto.CreateAttributeRequest;

import java.util.List;

public interface AttributeDefinitionService {
    AttributeDefinitionResponse create(CreateAttributeRequest request);
    List<AttributeDefinitionResponse> getAll();
}
