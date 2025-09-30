package com.cdl.escrow.mapper;


import com.cdl.escrow.dto.BuildPartnerAccountDTO;
import com.cdl.escrow.entity.BuildPartnerAccount;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildPartnerAccountMapper extends EntityMapper<BuildPartnerAccountDTO, BuildPartnerAccount> {

    @Mapping(source = "buildPartner", target = "buildPartnerDTO")
    BuildPartnerAccountDTO toDto(BuildPartnerAccount buildPartnerAccount);


    @Mapping(source = "buildPartnerDTO", target = "buildPartner")
    BuildPartnerAccount toEntity(BuildPartnerAccountDTO buildPartnerAccountDTO);
}
