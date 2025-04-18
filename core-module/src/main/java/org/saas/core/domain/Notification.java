package org.saas.core.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification extends Auditable {

    private String title;

    private String message;

    private String targetRole = "MODERATOR";

    public Notification() {}

    public Notification(String title, String message, String targetRole) {
        this.title = title;
        this.message = message;
        this.targetRole = targetRole;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }
}
