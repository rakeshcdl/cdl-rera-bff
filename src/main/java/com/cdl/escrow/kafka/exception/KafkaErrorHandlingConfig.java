package com.cdl.escrow.kafka.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

//@Configuration
public class KafkaErrorHandlingConfig {

    // retry 3 times with 2s delay; then dead-letter
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        DefaultErrorHandler handler = new DefaultErrorHandler(new FixedBackOff(2000L, 3L));
        handler.setAckAfterHandle(false);
        handler.setCommitRecovered(true); // commit after successfully sent to DLT
        return handler;
    }
}
