package com.notification.service.dto;

import lombok.Builder;

@Builder
public record UserPreferencesMessage(
         Long userId,
         boolean receiveEmail,
         boolean receiveSms,
         boolean receivePushNotifications,
         String preferredLanguage
) {
}
