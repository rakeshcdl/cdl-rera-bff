package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDTO implements Serializable {
    private Long id ;

    private String notificationId;

    private String userId;

    private String content;

    private boolean isRead;

    private Boolean deleted ;

    private boolean enabled ;
}
