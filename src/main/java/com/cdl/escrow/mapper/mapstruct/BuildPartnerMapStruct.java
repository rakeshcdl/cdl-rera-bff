package com.cdl.escrow.mapper.mapstruct;


import com.cdl.escrow.dto.record.BuildPartnerRecord;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.mapper.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface BuildPartnerMapStruct {

    BuildPartnerMapper INSTANCE = Mappers.getMapper(BuildPartnerMapper.class);

    @Mapping(source = "status", target = "status") // enum -> string default handled
    BuildPartnerRecord toDto(BuildPartner entity);
}
