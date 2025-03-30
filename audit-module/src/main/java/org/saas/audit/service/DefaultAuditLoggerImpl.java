package org.saas.audit.service;

import org.saas.core.AuditLogger;

public class DefaultAuditLoggerImpl implements AuditLogger {

    private final AuditLogService auditLogService;

    public DefaultAuditLoggerImpl(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    public void log(String actor, String action, String resource, String details) {
        auditLogService.log(actor, action, resource, details);
    }
}
