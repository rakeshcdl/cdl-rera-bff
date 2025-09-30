package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BinaryDataStoreDTO;
import com.cdl.escrow.entity.BinaryDataStore;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BinaryDataStoreMapper extends EntityMapper<BinaryDataStoreDTO, BinaryDataStore> {

    BinaryDataStoreDTO toDto(BinaryDataStore binaryDataStore);
    BinaryDataStore toEntity(BinaryDataStoreDTO binaryDataStoreDTO);
}
