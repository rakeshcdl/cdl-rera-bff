package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestFeeDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssestFee;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class,RealEstateAssestMapper.class })
public interface RealEstateAssestFeeMapper extends EntityMapper<RealEstateAssestFeeDTO, RealEstateAssestFee> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "reafCategory", target = "reafCategoryDTO")
    @Mapping(source = "reafCurrency", target = "reafCurrencyDTO")
    @Mapping(source = "reafFrequency", target = "reafFrequencyDTO")
    @Mapping(source = "reafAccountType", target = "reafAccountTypeDTO")
    RealEstateAssestFeeDTO toDto(RealEstateAssestFee realEstateAssestFee);

    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "reafCategoryDTO", target = "reafCategory")
    @Mapping(source = "reafCurrencyDTO", target = "reafCurrency")
    @Mapping(source = "reafAccountTypeDTO", target = "reafAccountType")
    RealEstateAssestFee toEntity(RealEstateAssestFeeDTO realEstateAssestFeeDTO);
}
