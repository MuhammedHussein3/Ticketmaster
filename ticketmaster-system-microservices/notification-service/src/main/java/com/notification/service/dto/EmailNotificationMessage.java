package com.notification.service.dto;

import java.time.LocalDateTime;

public record EmailNotificationMessage(
        Integer userId,
        String email,
        String subject,
        String messageBody,
        LocalDateTime sendTime
) {
}
