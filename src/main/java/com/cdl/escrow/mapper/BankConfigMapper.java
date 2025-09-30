

package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankConfigDTO;
import com.cdl.escrow.entity.BankConfig;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BankConfigMapper extends EntityMapper<BankConfigDTO, BankConfig> {

    BankConfigDTO toDto(BankConfig bankConfig);

    BankConfig toEntity(BankConfigDTO bankConfigDTO);

}
