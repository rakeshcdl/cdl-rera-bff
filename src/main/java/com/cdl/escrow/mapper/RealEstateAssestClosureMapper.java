package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestClosureDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestClosure;
import com.cdl.escrow.entity.RealEstateDocument;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class , RealEstateAssestMapper.class, RealEstateDocumentMapper.class})
public interface RealEstateAssestClosureMapper extends EntityMapper<RealEstateAssestClosureDTO, RealEstateAssestClosure> {

    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "reacDocument", target = "reacDocumentDTO")
    RealEstateAssestClosureDTO toDto(RealEstateAssestClosure realEstateAssestClosure);

    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "reacDocumentDTO", target = "reacDocument")
    RealEstateAssestClosure toEntity(RealEstateAssestClosureDTO realEstateAssestClosureDTO);
}
