package com.cdl.escrow.kafka.dto;

public record EscrowEvent(Long id, String type, String payload) {}

