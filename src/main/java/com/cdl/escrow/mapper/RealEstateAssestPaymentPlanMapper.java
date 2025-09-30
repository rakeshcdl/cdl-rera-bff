package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestPaymentPlanDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestPaymentPlan;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { RealEstateAssestMapper.class })
public interface RealEstateAssestPaymentPlanMapper extends EntityMapper<RealEstateAssestPaymentPlanDTO, RealEstateAssestPaymentPlan> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    RealEstateAssestPaymentPlanDTO toDto(RealEstateAssestPaymentPlan realEstateAssestPaymentPlan);


    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    RealEstateAssestPaymentPlan toEntity(RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO);
}
