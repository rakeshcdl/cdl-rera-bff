
package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.AppLanguageCodeDTO;
import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import com.cdl.escrow.dto.ApplicationModuleDTO;
import com.cdl.escrow.entity.AppLanguageCode;
import com.cdl.escrow.entity.AppLanguageTranslation;
import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring" , uses = {ApplicationModuleMapper.class, AppLanguageCodeMapper.class})

public interface AppLanguageTranslationMapper extends EntityMapper<AppLanguageTranslationDTO, AppLanguageTranslation> {

   @Mapping(target = "appLanguageCode", source = "appLanguageCode")//, qualifiedByName = "appLanguageCodeID")
   @Mapping(source = "applicationModule", target = "applicationModuleDTO")
    AppLanguageTranslationDTO toDto(AppLanguageTranslation entity);

   @Mapping(target = "appLanguageCode", source = "appLanguageCode")
   @Mapping(source = "applicationModuleDTO", target = "applicationModule")
    AppLanguageTranslation toEntity(AppLanguageTranslationDTO dto);

    List<AppLanguageTranslationDTO> toDtoList(List<AppLanguageTranslation> appLanguageTranslationList);


}
