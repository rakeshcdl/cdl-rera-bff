package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BuildPartnerContactDTO;
import com.cdl.escrow.entity.BuildPartnerContact;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
uses = { BuildPartnerMapper.class })
public interface BuildPartnerContactMapper extends EntityMapper<BuildPartnerContactDTO, BuildPartnerContact> {

    @Override
    @Mapping(source = "buildPartner", target = "buildPartnerDTO")
    BuildPartnerContactDTO toDto(BuildPartnerContact buildPartnerContact);

    @Override
    @Mapping(source = "buildPartnerDTO", target = "buildPartner")
    BuildPartnerContact toEntity(BuildPartnerContactDTO buildPartnerContactDTO);
}
