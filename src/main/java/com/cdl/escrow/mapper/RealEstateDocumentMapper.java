package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateDocumentDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class })
public interface RealEstateDocumentMapper  extends EntityMapper<RealEstateDocumentDTO, RealEstateDocument> {

    RealEstateDocumentMapper INSTANCE = Mappers.getMapper(RealEstateDocumentMapper.class);

    @Mapping(source = "documentType", target = "documentTypeDTO")
    RealEstateDocumentDTO toDto(RealEstateDocument realEstateDocument);

    @Mapping(source = "documentTypeDTO", target = "documentType")
    RealEstateDocument toEntity(RealEstateDocumentDTO realEstateDocumentDTO);
}
