package org.saas.audit.service;

public interface AuditLogService {
    void log(String actor, String action, String resource, String details);
}
