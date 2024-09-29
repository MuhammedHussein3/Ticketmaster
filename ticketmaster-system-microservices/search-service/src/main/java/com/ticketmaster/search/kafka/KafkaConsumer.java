package com.ticketmaster.search.kafka;


import com.ticketmaster.search.message.EventMessageCDC;
import com.ticketmaster.search.service.EventCDCService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final EventCDCService service;

    @KafkaListener(
            topics = "event.public.event",
            groupId = "event-group"
    )
    public void debeziumListener(EventMessageCDC eventMessageCDC){
        if (eventMessageCDC.getOp().equals("c"))
        {
            log.info("Created");
            log.info("Id{}", eventMessageCDC.getAfter().getEvent_id());
            try {
                service.createEvent(eventMessageCDC);
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        if (eventMessageCDC.getOp().equals("u")){
            log.info("Updated");
            log.info("ID{}", eventMessageCDC.getBefore().getEvent_id());
            log.info("Id{}", eventMessageCDC.getAfter().getEvent_id());
            try {
                service.updateEvent(eventMessageCDC);
            }catch (Exception e){
                throw new RuntimeException(e);
            }


        }
        if (eventMessageCDC.getOp().equals("d")){
            log.info("Deleted");
            log.info("ID{}", eventMessageCDC.getBefore().getEvent_id());
            log.info("Id{}", eventMessageCDC.getAfter().getEvent_id());

        }
        if (eventMessageCDC.getOp().equals("d")){
            service.deleteEvent(eventMessageCDC);
        }
    }
}
