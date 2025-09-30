package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerPaymentPlanDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerPaymentPlan;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { CapitalPartnerMapper.class })
public interface CapitalPartnerPaymentPlanMapper extends EntityMapper<CapitalPartnerPaymentPlanDTO, CapitalPartnerPaymentPlan> {

    @Mapping(source = "capitalPartner", target = "capitalPartnerDTO")
    CapitalPartnerPaymentPlanDTO toDto(CapitalPartnerPaymentPlan capitalPartnerPaymentPlan);


    @Mapping(source = "capitalPartnerDTO", target = "capitalPartner")
    CapitalPartnerPaymentPlan toEntity(CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO);
}
