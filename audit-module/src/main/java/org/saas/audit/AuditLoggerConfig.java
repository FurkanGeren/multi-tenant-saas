package org.saas.audit;

import org.saas.audit.service.DefaultAuditLoggerImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DefaultAuditLoggerImpl.class)
public class AuditLoggerConfig {
}