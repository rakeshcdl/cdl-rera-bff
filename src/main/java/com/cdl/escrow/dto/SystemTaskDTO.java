package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemTaskDTO implements Serializable {
    private Long id ;

    private String taskId;

    private String taskName;

    private String taskStatus;

    private long scheduledAt;

    private long completedAt;

    private Boolean deleted ;

    private boolean enabled ;
}
