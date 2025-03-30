package org.saas.audit;


import org.saas.core.AuditLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuditTestController {

    private final AuditLogger auditLogger;

    public AuditTestController(AuditLogger auditLogger) {
        this.auditLogger = auditLogger;
    }

    @GetMapping("/test-audit")
    public String testAudit() {
        auditLogger.log("furkan", "TEST", "Audit", "Deneme loglama");
        return "Audit log yazıldı!";
    }
}