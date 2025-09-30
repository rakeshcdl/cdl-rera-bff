package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import com.cdl.escrow.entity.RealEstateAssest;
import com.cdl.escrow.entity.RealEstateAssestBeneficiary;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class})
public interface RealEstateAssestBeneficiaryMapper extends EntityMapper<RealEstateAssestBeneficiaryDTO, RealEstateAssestBeneficiary> {

    @Mapping(source = "reabTranferType", target = "reabTranferTypeDTO")
    @Mapping(source = "reabExpenseType", target = "reabExpenseTypeDTO")
    @Mapping(source = "reabVendorSubType", target = "reabVendorSubTypeDTO")
    @Mapping(source = "reabContractorSubType", target = "reabContractorSubTypeDTO")
    @Mapping(source = "reabInfrastructureCategory", target = "reabInfrastructureCategoryDTO")
    @Mapping(source = "reabSalesCategory", target = "reabSalesCategoryDTO")
    @Mapping(source = "realEstateAssests", target = "realEstateAssestDTO")
    RealEstateAssestBeneficiaryDTO toDto(RealEstateAssestBeneficiary realEstateAssestBeneficiary);

    @Mapping(source = "reabTranferTypeDTO", target = "reabTranferType")
    @Mapping(source = "reabExpenseTypeDTO", target = "reabExpenseType")
    @Mapping(source = "reabVendorSubTypeDTO", target = "reabVendorSubType")
    @Mapping(source = "reabContractorSubTypeDTO", target = "reabContractorSubType")
    @Mapping(source = "reabInfrastructureCategoryDTO", target = "reabInfrastructureCategory")
    @Mapping(source = "reabSalesCategoryDTO", target = "reabSalesCategory")
    @Mapping(source = "realEstateAssestDTO", target = "realEstateAssests")
    RealEstateAssestBeneficiary toEntity(RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO);



}
