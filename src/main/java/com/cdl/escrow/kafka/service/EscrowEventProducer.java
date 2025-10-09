/*
package com.cdl.escrow.kafka.service;

import com.cdl.escrow.kafka.dto.EscrowEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class EscrowEventProducer {
    private final KafkaTemplate<String, EscrowEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public void send(EscrowEvent event) {
        kafkaTemplate.send(topic, String.valueOf(event.id()), event)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        // log & maybe persist for retry
                    }
                });
    }
}
*/
