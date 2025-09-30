package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationSettingMapper extends EntityMapper<ApplicationSettingDTO, ApplicationSetting> {

    @Mapping(source = "languageTranslation", target = "languageTranslationId")
    ApplicationSettingDTO toDto(ApplicationSetting applicationSetting);

    @Mapping(source = "languageTranslationId", target = "languageTranslation")
    ApplicationSetting toEntity(ApplicationSettingDTO applicationSettingDTO);


}
