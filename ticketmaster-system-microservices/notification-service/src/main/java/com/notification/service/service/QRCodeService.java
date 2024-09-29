package com.notification.service.service;

import com.google.zxing.WriterException;
import com.notification.service.dto.TicketDetailsMessage;

import java.io.IOException;
import java.util.List;

public interface QRCodeService {

     List<String> createAndSendQRCodes(TicketDetailsMessage ticketDetailsMessage) throws WriterException, IOException;
    }
