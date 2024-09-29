package com.notification.service.service.impl;

import com.notification.service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;


    private final String SOURCE_SENDER_MAIL = "muhammadhussein2312@gmail.com";
    @Override
    public void sendEmail(String destination, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setTo(destination);
            helper.setSubject(subject);
            helper.setFrom(SOURCE_SENDER_MAIL);


            helper.setText(message, true);

            mailSender.send(mimeMessage);
            log.info("HTML email sent to {} with subject: {}", destination, subject);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", destination, e.getMessage());
        }
    }

    // Additional method to process Thymeleaf templates
    public String buildEmail(String templateName, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);
        return templateEngine.process(templateName, context);
    }

    @Override
    public void sendEmailWithAttachments(String to, String subject, String body, List<String> attachmentPaths) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom(SOURCE_SENDER_MAIL);


            for (String filePath : attachmentPaths) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachments", e);
        }
    }
}
