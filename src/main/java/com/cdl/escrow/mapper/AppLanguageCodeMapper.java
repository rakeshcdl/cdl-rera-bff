package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.AppLanguageCodeDTO;
import com.cdl.escrow.entity.AppLanguageCode;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppLanguageCodeMapper extends EntityMapper<AppLanguageCodeDTO,AppLanguageCode> {

    AppLanguageCodeDTO toDto(AppLanguageCode entity);

    AppLanguageCode toEntity(AppLanguageCodeDTO dto);

    // Optional: For partial updates
   // void updateEntityFromDto(AppLanguageCodeDTO dto, @MappingTarget AppLanguageCode entity);

}
