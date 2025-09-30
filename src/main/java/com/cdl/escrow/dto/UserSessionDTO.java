package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDTO implements Serializable {
    private Long id ;

    private String sessionId;

    private String userId;

    private long createdAt;

    private long lastAccessedAt;

    private Boolean deleted ;

    private boolean enabled ;
}
