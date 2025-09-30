package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerUnitTypeDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitType;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CapitalPartnerUnitTypeMapper extends EntityMapper<CapitalPartnerUnitTypeDTO, CapitalPartnerUnitType> {

    CapitalPartnerUnitTypeDTO toDto(CapitalPartnerUnitType capitalPartnerUnitType);
    CapitalPartnerUnitType toEntity(CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO);
}
