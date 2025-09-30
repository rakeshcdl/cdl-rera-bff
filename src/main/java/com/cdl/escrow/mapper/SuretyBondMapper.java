package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.SuretyBondDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.SuretyBond;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class, RealEstateAssestMapper.class})
public interface SuretyBondMapper  extends EntityMapper<SuretyBondDTO, SuretyBond> {

    @Mapping(source = "suretyBondType", target = "suretyBondTypeDTO")
    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "issuerBank", target = "issuerBankDTO")
    @Mapping(source = "suretyBondStatus", target = "suretyBondStatusDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    SuretyBondDTO toDto(SuretyBond suretyBond);

    @Mapping(source = "suretyBondTypeDTO", target = "suretyBondType")
    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "issuerBankDTO", target = "issuerBank")
    @Mapping(source = "suretyBondStatusDTO", target = "suretyBondStatus")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    SuretyBond toEntity(SuretyBondDTO suretyBondDTO);
}
