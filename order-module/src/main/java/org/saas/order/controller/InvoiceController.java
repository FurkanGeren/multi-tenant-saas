package org.saas.order.controller;

import org.saas.core.annotation.Auditable;
import org.saas.core.annotation.ModuleAccess;
import org.saas.core.domain.enums.ModuleType;
import org.saas.order.dto.CancelInvoiceRequest;
import org.saas.order.dto.InvoiceResponse;
import org.saas.order.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PatchMapping("/{id}/pay")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    @Auditable(action = "INVOICE_PAID", resource = "INVOICE")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<Void> markInvoiceAsPaid(@PathVariable Long id) {
        invoiceService.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @Auditable(action = "CANCEL", resource = "INVOICE")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<Void> cancel(@PathVariable Long id,
                                       @RequestBody(required = false) CancelInvoiceRequest request)
    {
        invoiceService.cancelInvoice(id, request.reason());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ModuleAccess(ModuleType.USER)
    public ResponseEntity<InvoiceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getById(id));
    }

}
