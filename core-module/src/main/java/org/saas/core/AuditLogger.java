package org.saas.core;

public interface AuditLogger {
    void log(String actor, String action, String resource, String details);
}