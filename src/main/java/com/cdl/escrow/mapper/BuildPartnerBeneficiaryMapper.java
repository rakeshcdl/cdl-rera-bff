package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.entity.BuildPartner;
import com.cdl.escrow.entity.BuildPartnerBeneficiary;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BuildPartnerBeneficiaryMapper extends EntityMapper<BuildPartnerBeneficiaryDTO, BuildPartnerBeneficiary> {

    @Mapping(source = "bpbTransferType", target = "bpbTransferTypeDTO")
    @Mapping(source = "buildPartners" , target = "buildPartnerDTO")
    BuildPartnerBeneficiaryDTO toDto(BuildPartnerBeneficiary buildPartnerBeneficiary);

    @Mapping(source = "bpbTransferTypeDTO", target = "bpbTransferType")
    @Mapping( source = "buildPartnerDTO" , target = "buildPartners")
    BuildPartnerBeneficiary toEntity(BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO);



}
