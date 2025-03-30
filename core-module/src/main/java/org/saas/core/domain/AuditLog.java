package org.saas.core.domain;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends Auditable {

    @Column(name = "actor", nullable = false)
    private String actor; // Kim yaptı (email/id gibi)

    @Column(name = "action", nullable = false)
    private String action; // Örn: CREATE, UPDATE, DELETE

    @Column(name = "resource", nullable = false)
    private String resource; // Örn: User, Product

    @Column(name = "details", columnDefinition = "TEXT")
    private String details; // Ek bilgi, JSON formatında olabilir


    public AuditLog() {
    }

    public AuditLog(String actor, String action, String resource, String details) {
        this.actor = actor;
        this.action = action;
        this.resource = resource;
        this.details = details;
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



}