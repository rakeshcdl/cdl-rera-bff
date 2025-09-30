package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ConfigurationStoreDTO;
import com.cdl.escrow.entity.ConfigurationStore;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigurationStoreMapper extends EntityMapper<ConfigurationStoreDTO, ConfigurationStore> {

    ConfigurationStoreDTO toDto(ConfigurationStore configurationStore);
    ConfigurationStore toEntity(ConfigurationStoreDTO configurationStoreDTO);
}
