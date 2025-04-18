package org.saas.core.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notifications")
public class UserNotification extends Auditable {

    private Long notificationId;

    private Long userId;

    private boolean read = false;

    private LocalDateTime seenAt;

    public UserNotification() {}

    public UserNotification(Long notificationId, Long userId, boolean read, LocalDateTime seenAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.read = read;
        this.seenAt = seenAt;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(LocalDateTime seenAt) {
        this.seenAt = seenAt;
    }
}
