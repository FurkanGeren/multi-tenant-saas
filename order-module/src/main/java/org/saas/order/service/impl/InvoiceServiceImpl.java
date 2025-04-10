package org.saas.order.service.impl;

import org.saas.core.context.TenantContext;
import org.saas.core.domain.Invoice;
import org.saas.core.domain.Order;
import org.saas.core.domain.enums.InvoiceStatus;
import org.saas.core.exception.BusinessException;
import org.saas.order.dto.InvoiceResponse;
import org.saas.order.mapper.OrderMapper;
import org.saas.order.repository.InvoiceRepository;
import org.saas.order.repository.OrderRepository;
import org.saas.order.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper  orderMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateInvoiceForOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(); // TODO

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceCode());
        invoice.setOrder(order);
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(7));
        invoice.setTotal(order.getTotal());
        invoice.setStatus(InvoiceStatus.PENDING);

        invoiceRepository.save(invoice);
        System.out.println("✅ Fatura oluşturuldu: " + invoice.getInvoiceNumber());
    }

    @Override
    @Transactional
    public void markAsPaid(Long id) {
        setTenantSchema();
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Fatura bulunamadı"));

        invoice.setStatus(InvoiceStatus.PAID);
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void cancelInvoice(Long id, String reason) {
        setTenantSchema();
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Fatura bulunamadı"));

        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoice.setNotes(reason);
        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAll() {
        try {
            setTenantSchema();
            List<Invoice> invoices = invoiceRepository.findAllWithOrders();
            return invoices.stream()
                    .map(orderMapper::toResponse)
                    .toList();
        } finally {
            TenantContext.clear(); // 🔥 Context’i temizle
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getById(Long id) {
        try{
            setTenantSchema();

            Invoice invoice = invoiceRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Fatura bulunamadı"));

            return orderMapper.toResponse(invoice);
        }finally {
            TenantContext.clear();
        }

    }


    private void setTenantSchema() {
        String schema = TenantContext.getTenantSchema();
        if (schema == null) {
            throw new BusinessException("Tenant bilgisi bulunamadı.");
        }
        TenantContext.setTenantSchema(schema);
    }



    private String generateInvoiceCode() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 20250410
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "INV-" + date + "-" + random;
    }
}
