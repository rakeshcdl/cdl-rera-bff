package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerBankInfoDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerBankInfo;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class, CapitalPartnerMapper.class })
public interface CapitalPartnerBankInfoMapper  extends EntityMapper<CapitalPartnerBankInfoDTO, CapitalPartnerBankInfo> {


    @Mapping(source = "capitalPartner", target = "capitalPartnerDTO")
    @Mapping(source = "payMode", target = "payModeDTO")
    CapitalPartnerBankInfoDTO toDto(CapitalPartnerBankInfo capitalPartnerBankInfo);


    @Mapping(source = "capitalPartnerDTO", target = "capitalPartner")
    @Mapping(source = "payModeDTO", target = "payMode")
    CapitalPartnerBankInfo toEntity(CapitalPartnerBankInfoDTO capitalPartnerBankInfoDTO);
}
