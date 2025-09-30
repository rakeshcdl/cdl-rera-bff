package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ApplicationTableDesignDTO;
import com.cdl.escrow.entity.ApplicationTableDesign;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationTableDesignMapper extends EntityMapper<ApplicationTableDesignDTO, ApplicationTableDesign> {
    ApplicationTableDesignDTO toDto(ApplicationTableDesign applicationTableDesign);
    ApplicationTableDesign toEntity(ApplicationTableDesignDTO applicationTableDesignDTO);
}
