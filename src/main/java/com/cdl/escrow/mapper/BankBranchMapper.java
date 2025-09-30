package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BankBranchDTO;
import com.cdl.escrow.entity.BankBranch;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankBranchMapper  extends EntityMapper<BankBranchDTO, BankBranch> {

    BankBranchDTO toDto(BankBranch bankBranch);
    BankBranch toEntity(BankBranchDTO bankBranchDTO);
}
