/*
package com.cdl.escrow.kafka.service;


import com.cdl.escrow.kafka.dto.EscrowEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Slf4j
//@Component
public class EscrowEventConsumer {

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}" )
    public void onMessage(ConsumerRecord<String, EscrowEvent> record, Acknowledgment ack) {
        try {
            EscrowEvent event = record.value();
            // TODO: process event
            ack.acknowledge(); // commit offset after successful processing
        } catch (Exception e) {
          //  log.error("Processing failed, key={}, offset={}", record.key(), record.offset(), e);
            // Don't ack → record will be retried depending on your policy
            // Consider DLT for poison messages (see below)
        }
    }
}
*/
