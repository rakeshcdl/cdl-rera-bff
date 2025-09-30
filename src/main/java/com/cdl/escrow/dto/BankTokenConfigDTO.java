/**
 * BankTokenConfigDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.dto;


import com.cdl.escrow.entity.BankConfig;
import lombok.Data;

import java.io.Serializable;

@Data
public class BankTokenConfigDTO implements Serializable {

    private Long id;

    private String tokenUrl;

    private String method;

    private String headersJson;

    private String bodyTemplate;

    private String jsonPath;

    private BankConfig bankConfig;

    private Boolean deleted ;

    private boolean enabled ;
}
