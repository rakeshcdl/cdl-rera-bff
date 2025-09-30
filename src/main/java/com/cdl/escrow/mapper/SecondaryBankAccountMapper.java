package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.dto.SecondaryBankAccountDTO;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.entity.SecondaryBankAccount;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SecondaryBankAccountMapper  extends EntityMapper<RealEstateDocumentDTO, RealEstateDocument> {

    SecondaryBankAccountDTO toDto(SecondaryBankAccount secondaryBankAccount);
    SecondaryBankAccount toEntity(SecondaryBankAccountDTO secondaryBankAccountDTO);
}
