package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class})
public interface RealEstateAssestMapper  extends EntityMapper<RealEstateAssestDTO, RealEstateAssest> {

    @Mapping(source = "reaStatus", target = "reaStatusDTO")
    @Mapping(source = "reaType", target = "reaTypeDTO")
    @Mapping(source = "reaAccountStatus", target = "reaAccountStatusDTO")
    @Mapping(source = "reaConstructionCostCurrency", target = "reaConstructionCostCurrencyDTO")
    @Mapping(source = "buildPartner", target = "buildPartnerDTO")
    @Mapping(source = "reaBlockPaymentTypes", target = "reaBlockPaymentTypeDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    RealEstateAssestDTO toDto(RealEstateAssest realEstateAssest);


    @Mapping(source = "reaStatusDTO", target = "reaStatus")
    @Mapping(source = "reaTypeDTO", target = "reaType")
    @Mapping(source = "reaAccountStatusDTO", target = "reaAccountStatus")
    @Mapping(source = "reaConstructionCostCurrencyDTO", target = "reaConstructionCostCurrency")
    @Mapping(source = "buildPartnerDTO", target = "buildPartner")
    @Mapping(source = "reaBlockPaymentTypeDTO", target = "reaBlockPaymentTypes")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    RealEstateAssest toEntity(RealEstateAssestDTO realEstateAssestDTO);

    default RealEstateAssest fromId(Long id) {
        if (id == null) return null;
        RealEstateAssest entity = new RealEstateAssest();
        entity.setId(id);
        return entity;
    }

}
