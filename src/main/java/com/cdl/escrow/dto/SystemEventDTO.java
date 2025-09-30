package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemEventDTO implements Serializable {
    private Long id ;

    private String eventId;

    private String eventType;

    private String userId;

    private String timestamp;

    private String eventDetails;

    private Boolean deleted ;

    private boolean enabled ;
}
