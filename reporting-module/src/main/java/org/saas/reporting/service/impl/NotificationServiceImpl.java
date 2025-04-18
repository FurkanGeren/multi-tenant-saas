package org.saas.reporting.service.impl;

import org.saas.core.domain.Notification;
import org.saas.core.domain.UserNotification;
import org.saas.core.dto.UserResponse;
import org.saas.core.event.InvoiceReminderEvent;
import org.saas.reporting.client.UserModuleClient;
import org.saas.reporting.repository.NotificationRepository;
import org.saas.reporting.repository.UserNotificationRepository;
import org.saas.reporting.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {


    private final NotificationRepository notificationRepository;
    private final UserModuleClient userModuleClient;
    private final UserNotificationRepository userNotificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserModuleClient userModuleClient, UserNotificationRepository userNotificationRepository) {
        this.notificationRepository = notificationRepository;
        this.userModuleClient = userModuleClient;
        this.userNotificationRepository = userNotificationRepository;
    }

    @Override
    @Transactional
    public void sendInvoiceNotification(InvoiceReminderEvent event) {
        // 1. Notification tablosuna kayıt yapılır
        Notification notification = new Notification();
        notification.setTitle("Fatura Hatırlatma");
        notification.setMessage("Fatura " + event.getInvoiceNumber() + " yarın vadesini dolduracak.");
        notificationRepository.save(notification);

        // 2. MODERATOR kullanıcılar alınır
        List<UserResponse> moderators = userModuleClient.getModerators(event.getTenantSchema());

        // 3. Her biri için UserNotification oluştur
        List<UserNotification> userNotifications = moderators.stream()
                .map(user -> new UserNotification(notification.getId(), user.id(), false, null))
                .toList();

        userNotificationRepository.saveAll(userNotifications);
    }
}
