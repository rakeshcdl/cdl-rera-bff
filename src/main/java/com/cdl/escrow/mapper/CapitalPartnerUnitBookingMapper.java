package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.CapitalPartnerUnitBookingDTO;
import com.cdl.escrow.entity.CapitalPartnerUnitBooking;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CapitalPartnerUnitBookingMapper  extends EntityMapper<CapitalPartnerUnitBookingDTO, CapitalPartnerUnitBooking> {

    //@Mapping(source = "capitalPartner", target = "capitalPartnerDTO")
    CapitalPartnerUnitBookingDTO toDto(CapitalPartnerUnitBooking capitalPartnerUnitBooking);

   // @Mapping(source = "capitalPartner", target = "capitalPartnerDTO")
    CapitalPartnerUnitBooking toEntity(CapitalPartnerUnitBookingDTO capitalPartnerUnitBookingDTO);
}
