package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerUnitPurchaseDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnitPurchase;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class, CapitalPartnerMapper.class })
public interface CapitalPartnerUnitPurchaseMapper  extends EntityMapper<CapitalPartnerUnitPurchaseDTO, CapitalPartnerUnitPurchase> {

    @Mapping(source = "cpupCreditCurrency", target = "cpupCreditCurrencyDTO")
    @Mapping(source = "cpuPurchasePriceCurrency", target = "cpuPurchasePriceCurrencyDTO")
    @Mapping(source = "capitalPartnerUnit", target = "capitalPartnerUnitDTO")
    CapitalPartnerUnitPurchaseDTO toDto(CapitalPartnerUnitPurchase capitalPartnerUnitPurchase);


    @Mapping(source = "cpupCreditCurrencyDTO", target = "cpupCreditCurrency")
    @Mapping(source = "cpuPurchasePriceCurrencyDTO", target = "cpuPurchasePriceCurrency")
    @Mapping(source = "capitalPartnerUnitDTO", target = "capitalPartnerUnit")
    CapitalPartnerUnitPurchase toEntity(CapitalPartnerUnitPurchaseDTO capitalPartnerUnitPurchaseDTO);
}
