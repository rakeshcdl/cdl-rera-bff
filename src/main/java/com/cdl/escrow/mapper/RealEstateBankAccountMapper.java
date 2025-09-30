package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateBankAccountDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateBankAccount;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { RealEstateAssestMapper.class })
public interface RealEstateBankAccountMapper  extends EntityMapper<RealEstateBankAccountDTO, RealEstateBankAccount> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    RealEstateBankAccountDTO toDto(RealEstateBankAccount realEstateBankAccount);

    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    RealEstateBankAccount toEntity(RealEstateBankAccountDTO realEstateBankAccountDTO);
}
