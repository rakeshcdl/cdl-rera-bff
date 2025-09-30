package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemAuditLogDTO implements Serializable {
    private Long id ;

    private String eventId;

    private String eventType;

    private String userId;

    private String timestamp;

    private String details;

    private Boolean deleted ;

    private boolean enabled ;
}
