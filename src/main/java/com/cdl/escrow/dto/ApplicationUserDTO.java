package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserDTO implements Serializable {
    private Long id ;

    private String username;

    private String password;

    private String email;

    private Boolean deleted ;

    private boolean enabled ;
}
