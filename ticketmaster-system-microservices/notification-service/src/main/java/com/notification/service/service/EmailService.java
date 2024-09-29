package com.notification.service.service;

import java.util.List;
import java.util.Map;

public interface EmailService {

        void sendEmail(String destination, String subject, String body);

        void sendEmailWithAttachments(String to, String subject, String body, List<String> attachmentPaths);

        public String buildEmail(String templateName, Map<String, Object> model);

}
