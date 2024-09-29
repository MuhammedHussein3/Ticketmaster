package com.notification.service;

import com.notification.service.dto.QRCodeDetails;
import com.notification.service.dto.TicketDetailsMessage;
import com.notification.service.dto.User;
import com.notification.service.service.QRCodeService;
import com.notification.service.service.impl.NotificationService;
import com.notification.service.utils.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StartupApp implements CommandLineRunner {

    private final QRCodeService qrCodeService;
    private final NotificationService notificationService;
    @Override
    public void run(String... args) throws Exception {
//        Map<Long, String> map = new HashMap<>();
//
//        map.put(1L, "A1");
//        map.put(34L, "B3Down");
//        map.put(5L, "C1VIP");
//
//        Map<Long, User> map1 = new HashMap<>();
//        User user = User.builder()
//                        .firsName("Muhammad")
//                                .lastName("Hussein")
//                                        .age(22)
//                                                .build();
//        User user2 = User.builder()
//                .firsName("Sama")
//                .lastName("Hussein")
//                .age(22)
//                .build();
//        User use3r = User.builder()
//                .firsName("Ahmed")
//                .lastName("Ali")
//                .age(22)
//                .build();
//        map1.put(1L, user);
//        map1.put(34L, user2);
//        map1.put(5L, use3r);
//
//
//        TicketDetailsMessage ticketDetailsMessage = TicketDetailsMessage.builder()
//                .ticketIdSeat(map)
//                .eventId(1L)
//                .eventDate(LocalDateTime.now())
//                .eventName("Cairo Stadium")
//                .venueLocation(";kjdfas")
//                .reservedId(3424L)
//                .userTicketInfo(map1)
//                .build();
//        for (Map.Entry<Long, String> entry : map.entrySet()){
//            User user1 = map1.get(entry.getKey());
//            QRCodeDetails qrCodeDetails = QRCodeDetails.builder()
//                    .reservedId(ticketDetailsMessage.reservedId())
//                    .seatId(entry.getValue())
//                    .ticketId(entry.getKey())
//                    .firstName(user1.getFirsName())
//                    .lastName(user.getLastName())
//                    .age(user.getAge())
//                    .build();
//
//            notificationService.sendTicketDetailsWithQRCodes(ticketDetailsMessage);
////          List<String> qrCodePaths =  qrCodeService.createAndSendQRCodes(ticketDetailsMessage);
////
////          qrCodePaths.forEach(System.out::println);
//        }
    }
}
