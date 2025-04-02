package org.saas.product.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.saas.core.context.TenantContext;
import org.saas.core.domain.AttributeDefinition;

import org.saas.product.dto.AttributeDefinitionResponse;
import org.saas.product.dto.CreateAttributeRequest;
import org.saas.product.repository.AttributeDefinitionRepository;
import org.saas.product.service.AttributeDefinitionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeDefinitionServiceImpl implements AttributeDefinitionService {

    private final AttributeDefinitionRepository repository;
    private final ObjectMapper objectMapper;

    public AttributeDefinitionServiceImpl(AttributeDefinitionRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public AttributeDefinitionResponse create(CreateAttributeRequest request) {
        TenantContext.setTenantSchema(TenantContext.getTenantSchema()); // şema güvenliği için zorunlu

        AttributeDefinition def = new AttributeDefinition();
        def.setKey(request.key());
        def.setLabel(request.label());
        def.setType(request.type());
        def.setOptionsJson(serialize(request.options()));

        repository.save(def);

        return new AttributeDefinitionResponse(
                def.getId(),
                def.getKey(),
                def.getLabel(),
                def.getType(),
                request.options()
        );
    }

    @Override
    public List<AttributeDefinitionResponse> getAll() {
        TenantContext.setTenantSchema(TenantContext.getTenantSchema());

        return repository.findAll().stream()
                .map(def -> new AttributeDefinitionResponse(
                        def.getId(),
                        def.getKey(),
                        def.getLabel(),
                        def.getType(),
                        deserialize(def.getOptionsJson())
                ))
                .toList();
    }

    private String serialize(List<String> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return "[]";
        }
    }

    private List<String> deserialize(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return List.of();
        }
    }
}