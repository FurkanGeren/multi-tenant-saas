package org.saas.product.service;

import org.saas.product.dto.CreateProductRequest;
import org.saas.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getByIdProduct(Long id);
}
