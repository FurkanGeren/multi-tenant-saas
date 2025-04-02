package org.saas.core.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product_attributes")
public class ProductAttribute extends BaseEntity {


    @Column(nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_definition_id")
    private AttributeDefinition definition;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public AttributeDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(AttributeDefinition definition) {
        this.definition = definition;
    }
}