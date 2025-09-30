package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestFeeHistoryDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestFee;
import com.cdl.escrow.entity.RealEstateAssestFeeHistory;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class , RealEstateAssestMapper.class, RealEstateAssestFeeMapper.class})
public interface RealEstateAssestFeeHistoryMapper extends EntityMapper<RealEstateAssestFeeHistoryDTO, RealEstateAssestFeeHistory> {

    @Mapping(source = "realEstateAssestFee", target = "realEstateAssestFeeDTO")
    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    RealEstateAssestFeeHistoryDTO toDto(RealEstateAssestFeeHistory realEstateAssestFeeHistory);

    @Mapping(source = "realEstateAssestFeeDTO", target = "realEstateAssestFee")
    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    RealEstateAssestFeeHistory toEntity(RealEstateAssestFeeHistoryDTO realEstateAssestFeeHistoryDTO);
}
