package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionDTO implements Serializable {
    private Long id ;

    private String userId;

    private String permissionType;

    private boolean isGranted;

    private Boolean deleted ;

    private boolean enabled ;
}
