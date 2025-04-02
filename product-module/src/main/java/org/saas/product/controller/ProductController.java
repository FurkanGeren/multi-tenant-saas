package org.saas.product.controller;

import jakarta.validation.Valid;
import org.saas.core.annotation.Auditable;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.product.dto.CreateProductRequest;
import org.saas.product.dto.ProductResponse;
import org.saas.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @Auditable(action = "CREATE", resource = "Product")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest request) {
        ProductResponse created = productService.createProduct(request);
        return ResponseEntity.ok(created);
    }
}