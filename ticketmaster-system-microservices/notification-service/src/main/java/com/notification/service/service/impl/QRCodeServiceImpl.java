package com.notification.service.service.impl;

import com.google.zxing.WriterException;
import com.notification.service.dto.QRCodeDetails;
import com.notification.service.dto.TicketDetailsMessage;
import com.notification.service.dto.User;
import com.notification.service.mapper.Mapper;
import com.notification.service.service.QRCodeService;
import com.notification.service.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private final Mapper mapper;
    private static final String OUTPUT_DIR = "D:\\GitHub\\Ticketmaster\\ticketmaster-system-microservices\\notification-service\\QRCode\\";


    public List<String> createAndSendQRCodes(TicketDetailsMessage ticketDetailsMessage) throws WriterException, IOException {


        List<String> qrCodePaths = new ArrayList<>();
        for (Map.Entry<Long, String> entry : ticketDetailsMessage.ticketIdSeat().entrySet()) {
            Long ticketId = entry.getKey();
            String seatId = entry.getValue();
            User user =  ticketDetailsMessage.userTicketInfo().get(ticketId);
            QRCodeDetails qrCodeDetails = mapper.convertToQRCodeDetails(ticketId, seatId, ticketDetailsMessage, user);

            String qrCodePath = QRCodeGenerator.generateQrcode(qrCodeDetails, OUTPUT_DIR);

            qrCodePaths.add(qrCodePath);
        }
        return qrCodePaths;
    }


}
