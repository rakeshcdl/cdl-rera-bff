package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.SuretyBondRecoveryDTO;
import com.cdl.escrow.entity.SuretyBondRecovery;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuretyBondRecoveryMapper  extends EntityMapper<SuretyBondRecoveryDTO, SuretyBondRecovery> {

    SuretyBondRecoveryDTO toDto(SuretyBondRecovery suretyBondRecovery);
    SuretyBondRecovery toEntity(SuretyBondRecoveryDTO suretyBondRecoveryDTO);
}
