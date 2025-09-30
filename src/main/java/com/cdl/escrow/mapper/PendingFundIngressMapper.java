package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.PendingFundIngress;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class })
public interface PendingFundIngressMapper  extends EntityMapper<PendingFundIngressDTO, PendingFundIngress> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "capitalPartnerUnit", target = "capitalPartnerUnitDTO")
    @Mapping(source = "bucketType", target = "bucketTypeDTO")
    @Mapping(source = "depositMode", target = "depositModeDTO")
    @Mapping(source = "subDepositType", target = "subDepositTypeDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    PendingFundIngressDTO toDto(PendingFundIngress pendingFundIngress);

    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "capitalPartnerUnitDTO", target = "capitalPartnerUnit")
    @Mapping(source = "bucketTypeDTO", target = "bucketType")
    @Mapping(source = "depositModeDTO", target = "depositMode")
    @Mapping(source = "subDepositTypeDTO", target = "subDepositType")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    PendingFundIngress toEntity(PendingFundIngressDTO pendingFundIngressDTO);
}
