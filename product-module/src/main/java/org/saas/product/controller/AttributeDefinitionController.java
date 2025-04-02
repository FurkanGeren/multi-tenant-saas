package org.saas.product.controller;

import jakarta.validation.Valid;
import org.saas.core.annotation.Auditable;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.product.dto.AttributeDefinitionResponse;
import org.saas.product.dto.CreateAttributeRequest;
import org.saas.product.service.AttributeDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attributes")
public class AttributeDefinitionController {

    private final AttributeDefinitionService attributeDefinitionService;

    public AttributeDefinitionController(AttributeDefinitionService attributeDefinitionService) {
        this.attributeDefinitionService = attributeDefinitionService;
    }

    @PostMapping
    @Auditable(action = "CREATE", resource = "AttributeDefinition")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<AttributeDefinitionResponse> create(@RequestBody @Valid CreateAttributeRequest request) {
        AttributeDefinitionResponse created = attributeDefinitionService.create(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<List<AttributeDefinitionResponse>> getAll() {
        return ResponseEntity.ok(attributeDefinitionService.getAll());
    }
}