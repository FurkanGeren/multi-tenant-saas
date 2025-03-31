package org.saas.core.event;


import java.io.Serializable;

public class AuditEvent {
    private String actor;
    private String action;
    private String resource;
    private String details;
    private String tenantSchema;



    public String getTenantSchema() {
        return tenantSchema;
    }

    public void setTenantSchema(String tenantSchema) {
        this.tenantSchema = tenantSchema;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public AuditEvent(String actor, String action, String resource, String details, String tenantSchema) {
        this.actor = actor;
        this.action = action;
        this.resource = resource;
        this.details = details;
        this.tenantSchema = tenantSchema;
    }
}
