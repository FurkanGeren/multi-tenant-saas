package org.saas.product.repository;

import org.saas.core.domain.AttributeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, Long> {
}
