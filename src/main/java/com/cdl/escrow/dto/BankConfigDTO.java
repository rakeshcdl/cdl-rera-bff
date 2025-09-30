
package com.cdl.escrow.dto;

import com.cdl.escrow.entity.BankTokenConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BankConfigDTO implements Serializable {

    private Long id;

    private String bankCode;

    private String apiName;

    private String apiUrl;

    private String httpMethod;

    private String bodyTemplate; // In JSON Format

    private Boolean encryptionEnabled;

    private String encryptedFields; // comma-separated, we can add multipls fields here

    private Boolean retryEnabled;

    private String fallbackMessage;

    private Boolean deleted ;

    private boolean enabled ;

    // private transient List<BankApiHeaderDTO> headerDTOS;

   // private transient List<BankApiParameterDTO> parameterDTOS;

    private transient BankTokenConfig bankTokenConfig;
}
