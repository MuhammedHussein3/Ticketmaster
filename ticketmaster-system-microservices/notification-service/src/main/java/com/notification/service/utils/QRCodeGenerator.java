package com.notification.service.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.notification.service.dto.QRCodeDetails;
import com.notification.service.dto.TicketDetailsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QRCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);

    // Make QR code size configurable
    private static final int QR_CODE_WIDTH = 400;
    private static final int QR_CODE_HEIGHT = 400;

    // This method returns the path where the QR code image is stored
    public static String generateQrcode(QRCodeDetails qrCodeDetails, String outputDir) throws WriterException, IOException {
        Objects.requireNonNull(outputDir, "Output directory cannot be null");

        // Generate the QR code content
        String qrCodeText = buildQRCodeContent(qrCodeDetails);

        // Build the file path using the details from QRCodeDetails
        String qrCodeFileName = buildQRCodeFileName(qrCodeDetails, outputDir);

        // Write the QR code to the specified file
        writeQRCodeToFile(qrCodeText, qrCodeFileName);

        logger.info("QR code generated for ticket ID {} and saved at {}", qrCodeDetails.ticketId(), qrCodeFileName);

        return qrCodeFileName;
    }

    // Method to build the text for the QR code
    private static String buildQRCodeContent(QRCodeDetails qrCodeDetails) {
        return "Ticket-ID: " + qrCodeDetails.ticketId() +
                "\nSeat-ID: " + qrCodeDetails.seatId() +
                "\nReserved-ID: " + qrCodeDetails.reservedId() +
                "\nUser-ID: " + qrCodeDetails.userId() +
                "\nFirst Name: " + qrCodeDetails.firstName() +
                "\nLast Name: " + qrCodeDetails.lastName() +
                "\nAge: " + qrCodeDetails.age();
    }

    // Method to build the QR code file name
    private static String buildQRCodeFileName(QRCodeDetails qrCodeDetails, String outputDir) {
        return outputDir + qrCodeDetails.ticketId() + "-" +
                qrCodeDetails.seatId() + "-" +
                qrCodeDetails.reservedId() + "-" +
                qrCodeDetails.userId() + "-QRCODE.png";
    }

    // Method to generate and write QR code to file
    private static void writeQRCodeToFile(String qrCodeText, String qrCodeFileName) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);

        Path path = FileSystems.getDefault().getPath(qrCodeFileName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}