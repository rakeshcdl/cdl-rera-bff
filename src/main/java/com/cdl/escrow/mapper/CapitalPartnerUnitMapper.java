package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.CapitalPartnerUnit;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ApplicationSettingMapper.class, CapitalPartnerMapper.class,RealEstateAssestMapper.class,
CapitalPartnerUnitBookingMapper.class})
public interface CapitalPartnerUnitMapper  extends EntityMapper<CapitalPartnerUnitDTO, CapitalPartnerUnit> {

    CapitalPartnerUnitMapper INSTANCE = Mappers.getMapper(CapitalPartnerUnitMapper.class);

    @Mapping(source = "partnerUnit", target = "partnerUnitDTO")
    @Mapping(source = "capitalPartnerUnitType", target = "capitalPartnerUnitTypeDTO")
    @Mapping(source = "realEstateAssest", target = "realEstateAssestDTO")
    @Mapping(source = "unitStatus", target = "unitStatusDTO")
    @Mapping(source = "propertyId", target = "propertyIdDTO")
    @Mapping(source = "paymentPlanType", target = "paymentPlanTypeDTO")
    @Mapping(source = "capitalPartnerUnitBooking", target = "capitalPartnerUnitBookingDTO")
    CapitalPartnerUnitDTO toDto(CapitalPartnerUnit capitalPartnerUnit);

    @Mapping(source = "partnerUnitDTO", target = "partnerUnit")
    @Mapping(source = "capitalPartnerUnitTypeDTO", target = "capitalPartnerUnitType")
    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssest")
    @Mapping(source = "unitStatusDTO", target = "unitStatus")
    @Mapping(source = "propertyIdDTO", target = "propertyId")
    @Mapping(source = "paymentPlanTypeDTO", target = "paymentPlanType")
    @Mapping(source = "capitalPartnerUnitBookingDTO", target = "capitalPartnerUnitBooking")
    CapitalPartnerUnit toEntity(CapitalPartnerUnitDTO capitalPartnerUnitDTO);
}
