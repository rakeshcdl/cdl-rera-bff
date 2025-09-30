package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankApiHeaderDTO;
import com.cdl.escrow.entity.BankApiHeader;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankApiHeaderMapper extends EntityMapper<BankApiHeaderDTO, BankApiHeader> {

    BankApiHeaderDTO toDto(BankApiHeader bankApiHeader);

    BankApiHeader toEntity(BankApiHeaderDTO bankApiHeaderDTO);
}
