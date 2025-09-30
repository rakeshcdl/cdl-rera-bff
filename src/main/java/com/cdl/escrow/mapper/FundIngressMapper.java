package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FundIngressMapper  extends EntityMapper<ProcessedFundIngressDTO, ProcessedFundIngress> {
    ProcessedFundIngressDTO toDto(ProcessedFundIngress fundIngress);
    ProcessedFundIngress toEntity(ProcessedFundIngressDTO fundIngressDTO);
}
