package com.cdl.escrow.dto;

import com.cdl.escrow.entity.ApplicationSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateAssestFeeDTO implements Serializable {
    private Long id;

    private Double reafAmount;

    private Double reafDebitAmount;

    private Double reafTotalAmount;

    private ZonedDateTime reafCalender;

    private ZonedDateTime reafCollectionDate;

    private ZonedDateTime reafNextRecoveryDate;

    private Double reafVatPercentage;

    private Boolean reafCollected;

    private Double reafFeePercentage;

    private RealEstateAssestDTO realEstateAssestDTO;

    private ApplicationSettingDTO reafCategoryDTO;

    private ApplicationSettingDTO reafCurrencyDTO;

    private ApplicationSettingDTO reafFrequencyDTO;

    private ApplicationSettingDTO reafAccountTypeDTO;

    private Boolean deleted ;

    private boolean enabled ;
    // private Set<RealEstateAssestFeeHistoryDTO> realEstateAssestFeeHistoryDTOS ;
}
