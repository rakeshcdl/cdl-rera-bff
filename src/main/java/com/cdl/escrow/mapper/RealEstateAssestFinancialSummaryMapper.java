package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestFinancialSummaryDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestFinancialSummary;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { RealEstateAssestMapper.class })
public interface RealEstateAssestFinancialSummaryMapper  extends EntityMapper<RealEstateAssestFinancialSummaryDTO, RealEstateAssestFinancialSummary> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    RealEstateAssestFinancialSummaryDTO toDto(RealEstateAssestFinancialSummary realEstateAssestFinancialSummary);

    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    RealEstateAssestFinancialSummary toEntity(RealEstateAssestFinancialSummaryDTO realEstateAssestFinancialSummaryDTO);
}
