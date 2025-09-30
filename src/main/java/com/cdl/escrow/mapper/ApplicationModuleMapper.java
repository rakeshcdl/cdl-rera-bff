package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ApplicationModuleDTO;
import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationModuleMapper extends EntityMapper<ApplicationModuleDTO, ApplicationModule> {

    ApplicationModuleDTO toDto(ApplicationModule applicationModule);
    ApplicationModule toEntity(ApplicationModuleDTO applicationModuleDTO);
}
