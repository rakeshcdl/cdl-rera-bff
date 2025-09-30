package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemLogDTO implements Serializable {
    private Long id ;

    private String logId;

    private String logLevel;

    private String message;

    private String timestamp;

    private Boolean deleted ;

    private boolean enabled ;
}
