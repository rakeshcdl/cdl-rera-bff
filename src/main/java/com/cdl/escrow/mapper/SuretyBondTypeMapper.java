package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.SuretyBondTypeDTO;
import com.cdl.escrow.entity.SuretyBondType;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuretyBondTypeMapper  extends EntityMapper<SuretyBondTypeDTO, SuretyBondType> {

    SuretyBondTypeDTO toDto(SuretyBondType suretyBondType);
    SuretyBondType toEntity(SuretyBondTypeDTO suretyBondTypeDTO);
}
