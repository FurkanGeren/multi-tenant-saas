package org.saas.order.repository;

import org.saas.core.domain.Invoice;
import org.saas.core.domain.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @EntityGraph(attributePaths = {"order", "order.items"})
    @Query("SELECT i FROM Invoice i")
    List<Invoice> findAllWithOrders();


    List<Invoice> findByStatusAndDueDateBefore(InvoiceStatus status, LocalDate date);

    List<Invoice> findByStatusAndDueDateEquals(InvoiceStatus status, LocalDate date);

}
