
package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankApiParameterDTO;
import com.cdl.escrow.entity.BankApiParameter;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankApiParameterMapper extends EntityMapper<BankApiParameterDTO, BankApiParameter> {

    BankApiParameterDTO toDto(BankApiParameter bankApiParameter);
    BankApiParameter toEntity(BankApiParameterDTO bankApiParameterDTO);
}
