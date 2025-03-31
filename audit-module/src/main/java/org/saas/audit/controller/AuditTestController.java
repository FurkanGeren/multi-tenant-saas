package org.saas.audit.controller;


import org.saas.core.audit.AuditLogger;
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


}