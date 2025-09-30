
package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankAccountDTO;
import com.cdl.escrow.entity.BankAccount;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper  extends EntityMapper<BankAccountDTO, BankAccount> {

    BankAccountDTO toDto(BankAccount bankAccount);

    BankAccount toEntity(BankAccountDTO financialInstitutionDTO);
}
