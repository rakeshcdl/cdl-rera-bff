package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring" , uses = ApplicationSettingMapper.class)
public interface BuildPartnerMapper  extends EntityMapper<BuildPartnerDTO, BuildPartner> {

    BuildPartnerMapper INSTANCE = Mappers.getMapper(BuildPartnerMapper.class);

    @Mapping(source = "bpRegulator", target = "bpRegulatorDTO")
    @Mapping(source = "bpActiveStatus", target = "bpActiveStatusDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    BuildPartnerDTO toDto(BuildPartner buildPartner);

    @Mapping(source = "bpRegulatorDTO", target = "bpRegulator")
    @Mapping(source = "bpActiveStatusDTO", target = "bpActiveStatus")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    BuildPartner toEntity(BuildPartnerDTO buildPartnerDTO);

}
