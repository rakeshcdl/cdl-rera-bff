package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.SuretyBondReleaseDTO;
import com.cdl.escrow.entity.SuretyBondRelease;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuretyBondReleaseMapper  extends EntityMapper<SuretyBondReleaseDTO, SuretyBondRelease> {

    SuretyBondReleaseDTO toDto(SuretyBondRelease suretyBondRelease);
    SuretyBondRelease toEntity(SuretyBondReleaseDTO suretyBondReleaseDTO);
}
