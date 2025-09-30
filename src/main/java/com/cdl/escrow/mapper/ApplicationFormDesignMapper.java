package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ApplicationFormDesignDTO;
import com.cdl.escrow.entity.ApplicationFormDesign;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationFormDesignMapper extends EntityMapper<ApplicationFormDesignDTO, ApplicationFormDesign> {

    ApplicationFormDesignDTO toDto(ApplicationFormDesign applicationFormDesign);
    ApplicationFormDesign toEntity(ApplicationFormDesignDTO applicationFormDesignDTO);
}
