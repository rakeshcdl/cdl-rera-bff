/**
 * LoginRequestDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String username;

    private String email;

    private String password;


}
