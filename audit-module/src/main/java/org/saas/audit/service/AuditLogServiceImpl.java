package org.saas.audit.service;

import org.saas.audit.repository.AuditLogRepository;
import org.saas.core.domain.AuditLog;
import org.saas.core.tenant.TenantContext;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void log(String actor, String action, String resource, String details) {

        String tenant = TenantContext.getTenantSchema();

        TenantContext.setTenantSchema(tenant);


        AuditLog auditLog = new AuditLog();
        auditLog.setActor(actor);
        auditLog.setAction(action);
        auditLog.setResource(resource);
        auditLog.setDetails(details);

        auditLogRepository.save(auditLog);
    }
}
