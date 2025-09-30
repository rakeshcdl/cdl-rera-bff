package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import com.cdl.escrow.dto.ApplicationConfigurationDTO;
import com.cdl.escrow.entity.AppLanguageTranslation;
import com.cdl.escrow.entity.ApplicationConfiguration;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApplicationConfigurationMapper extends EntityMapper<AppLanguageTranslationDTO, AppLanguageTranslation> {

    ApplicationConfigurationDTO toDto(ApplicationConfiguration entity);

    ApplicationConfiguration toEntity(ApplicationConfigurationDTO dto);

    // Optional: For partial updates
    void updateEntityFromDto(ApplicationConfigurationDTO dto, @MappingTarget ApplicationConfiguration entity);

}
