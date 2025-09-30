package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuretyBondReleaseDTO implements Serializable {
    private Long id ;

    private ZonedDateTime suretyBondReleaseRequestDate;

    private SuretyBondDTO suretyBondDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
