package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.PrimaryBankAccountDTO;
import com.cdl.escrow.entity.PrimaryBankAccount;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrimaryBankAccountMapper extends EntityMapper<PrimaryBankAccountDTO, PrimaryBankAccount> {

    PrimaryBankAccountDTO toDto(PrimaryBankAccount primaryBankAccount);
    PrimaryBankAccount toEntity(PrimaryBankAccountDTO primaryBankAccountDTO);
}
