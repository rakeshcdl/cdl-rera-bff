package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class })
public interface ProcessedFundIngressMapper extends EntityMapper<ProcessedFundIngressDTO, ProcessedFundIngress> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "capitalPartnerUnit", target = "capitalPartnerUnitDTO")
    @Mapping(source = "bucketType", target = "bucketTypeDTO")
    @Mapping(source = "bucketSubType", target = "bucketSubTypeDTO")
    @Mapping(source = "depositMode", target = "depositModeDTO")
    @Mapping(source = "subDepositType", target = "subDepositTypeDTO")
  //  @Mapping(source = "pendingFundIngress", target = "subDepositTypeDTO")
    ProcessedFundIngressDTO toDto(ProcessedFundIngress processedFundIngress);


    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "capitalPartnerUnitDTO", target = "capitalPartnerUnit")
    @Mapping(source = "bucketTypeDTO", target = "bucketType")
    @Mapping(source = "bucketSubTypeDTO", target = "bucketSubType")
    @Mapping(source = "depositModeDTO", target = "depositMode")
    @Mapping(source = "subDepositTypeDTO", target = "subDepositType")
   // @Mapping(source = "subDepositType", target = "pendingFundIngress")
    ProcessedFundIngress toEntity(ProcessedFundIngressDTO processedFundIngressDTO);
}
