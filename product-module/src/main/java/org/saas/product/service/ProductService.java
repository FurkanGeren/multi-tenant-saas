package org.saas.product.service;

import org.saas.product.dto.CreateProductRequest;
import org.saas.product.dto.ProductResponse;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
}
