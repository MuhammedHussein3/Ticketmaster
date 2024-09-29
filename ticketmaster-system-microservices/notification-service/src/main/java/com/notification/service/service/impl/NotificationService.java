package com.notification.service.service.impl;

import com.google.zxing.WriterException;
import com.notification.service.dto.BookingConfirmationMessage;
import com.notification.service.dto.TicketDetailsMessage;
import com.notification.service.service.EmailService;
import com.notification.service.service.QRCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;
    private final QRCodeService qrCodeService;

    public void sendBookingConfirmation(BookingConfirmationMessage bookingMessage) {
        String subject = String.format("Booking Confirmation for %s", bookingMessage.eventName());


        Map<String, Object> model = new HashMap<>();
        model.put("eventName", bookingMessage.eventName());
        model.put("venueName", bookingMessage.venueName());
        model.put("venueLocation", bookingMessage.venueLocation());
        model.put("category", bookingMessage.category());
        model.put("eventDate", bookingMessage.eventDate().toString());
        model.put("bookingTime", bookingMessage.bookingTime().toString());

        String htmlContent = emailService.buildEmail("booking-confirmation", model);


        emailService.sendEmail("muhammadhussein2312@gmail.com", subject, htmlContent);
    }

    public void sendTicketDetailsWithQRCodes(TicketDetailsMessage message) throws IOException, WriterException {

        String subject = "Your Tickets for Event: " + message.eventName();
        String body = "Dear User,\n\nHere are your QR codes for the event: " + message.eventName() + " on " + message.eventDate() + ".\n\nBest regards,\nEvent Booking Team";

        List<String> qrCodePaths  = qrCodeService.createAndSendQRCodes(message);
        emailService.sendEmailWithAttachments("muhammadhussein2312@gmail.com", subject, body, qrCodePaths);
    }
}
