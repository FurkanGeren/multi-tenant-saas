package org.saas.core.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceReminderEvent implements Serializable {

    private Long invoiceId;
    private String invoiceNumber;
    private LocalDate dueDate;
    private BigDecimal total;
    private String tenantSchema;

    public InvoiceReminderEvent() {

    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getTenantSchema() {
        return tenantSchema;
    }

    public void setTenantSchema(String tenantSchema) {
        this.tenantSchema = tenantSchema;
    }

    public InvoiceReminderEvent(Long invoiceId, String invoiceNumber, LocalDate dueDate, BigDecimal total, String tenantSchema) {
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.dueDate = dueDate;
        this.total = total;
        this.tenantSchema = tenantSchema;
    }
}
