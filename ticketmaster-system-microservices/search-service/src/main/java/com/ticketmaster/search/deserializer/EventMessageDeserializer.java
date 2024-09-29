package com.ticketmaster.search.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ticketmaster.search.message.EventMessageCDC;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

public class EventMessageDeserializer implements Deserializer<EventMessageCDC> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public EventMessageCDC deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, EventMessageCDC.class);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EventMessageCDC deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public EventMessageCDC deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
