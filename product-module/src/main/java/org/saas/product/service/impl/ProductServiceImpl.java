package org.saas.product.service.impl;

import org.saas.core.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.saas.core.context.ActorContext;
import org.saas.core.context.TenantContext;
import org.saas.core.domain.AttributeDefinition;
import org.saas.core.domain.Product;
import org.saas.core.domain.ProductAttribute;
import org.saas.core.exception.BusinessException;
import org.saas.core.utils.JwtUtil;
import org.saas.product.dto.AttributeResponse;
import org.saas.product.dto.CreateProductRequest;
import org.saas.product.dto.ProductResponse;
import org.saas.product.repository.AttributeDefinitionRepository;
import org.saas.product.repository.ProductRepository;
import org.saas.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AttributeDefinitionRepository attributeDefinitionRepository;
    private final JwtUtil jwtUtil;

    public ProductServiceImpl(ProductRepository productRepository, AttributeDefinitionRepository attributeDefinitionRepository, JwtUtil jwtUtil) {
        this.productRepository = productRepository;
        this.attributeDefinitionRepository = attributeDefinitionRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {

        setTenantSchema();

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setActive(request.active());

        List<ProductAttribute> attributes = request.attributes().stream()
                .map(attrReq -> {
                    AttributeDefinition def = attributeDefinitionRepository.findById(attrReq.definitionId())
                            .orElseThrow(() -> new BusinessException("Attribute tanımı bulunamadı: ID = " + attrReq.definitionId()));
                    ProductAttribute attr = new ProductAttribute();
                    attr.setDefinition(def);
                    attr.setValue(attrReq.value());
                    return attr;
                }).toList();

        product.addAttributes(attributes);

        ActorContext.setActor(jwtUtil.extractUsername());

        productRepository.save(product);

        return getProductResponse(product);
    }



    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        setTenantSchema();
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::getProductResponse)
                .toList();
    }

    @Override
    public ProductResponse getByIdProduct(Long id) {
        setTenantSchema();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Urun bulunamadi"));

        return getProductResponse(product);
    }


    // PRIVATE FUNC
    private void setTenantSchema() {
        String schema = TenantContext.getTenantSchema();
        if (schema == null) {
            throw new BusinessException("Tenant bilgisi bulunamadı.");
        }
        TenantContext.setTenantSchema(schema);
    }

    private ProductResponse getProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.isActive(),
                product.getAttributes().stream()
                        .map(attr -> new AttributeResponse(
                                attr.getDefinition().getKey(),
                                attr.getDefinition().getLabel(),
                                attr.getValue()
                        )).toList()
        );
    }
}
