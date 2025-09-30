package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.FinancialInstitutionDTO;
import com.cdl.escrow.entity.FinancialInstitution;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FinancialInstitutionMapper  extends EntityMapper<FinancialInstitutionDTO, FinancialInstitution> {

    FinancialInstitutionDTO toDto(FinancialInstitution financialInstitution);
    FinancialInstitution toEntity(FinancialInstitutionDTO financialInstitutionDTO);
}
