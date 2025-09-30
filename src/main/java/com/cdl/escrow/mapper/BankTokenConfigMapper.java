
package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankTokenConfigDTO;
import com.cdl.escrow.entity.BankTokenConfig;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankTokenConfigMapper extends EntityMapper<BankTokenConfigDTO, BankTokenConfig> {
    BankTokenConfigDTO toDto(BankTokenConfig bankTokenConfig);
    BankTokenConfig toEntity(BankTokenConfigDTO bankTokenConfigDTO);
}
