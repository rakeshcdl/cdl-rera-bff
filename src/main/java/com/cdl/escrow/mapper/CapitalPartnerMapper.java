package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ApplicationSettingMapper.class, CapitalPartnerUnitMapper.class})
public interface CapitalPartnerMapper  extends EntityMapper<CapitalPartnerDTO, CapitalPartner> {

    @Mapping(source = "documentType", target = "documentTypeDTO")
    @Mapping(source = "countryOption", target = "countryOptionDTO")
    @Mapping(source = "investorType", target = "investorTypeDTO")
    @Mapping(source = "capitalPartnerUnit", target = "capitalPartnerUnitDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    CapitalPartnerDTO toDto(CapitalPartner capitalPartner);


    @Mapping(source = "documentTypeDTO", target = "documentType")
    @Mapping(source = "countryOptionDTO", target = "countryOption")
    @Mapping(source = "investorTypeDTO", target = "investorType")
    @Mapping(source = "capitalPartnerUnitDTO", target = "capitalPartnerUnit")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    CapitalPartner toEntity(CapitalPartnerDTO capitalPartnerDTO);
}
