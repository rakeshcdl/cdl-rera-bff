package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.BuildPartnerFeesDTO;
import com.cdl.escrow.entity.ApplicationSetting;
import com.cdl.escrow.entity.BuildPartnerFees;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = { ApplicationSettingMapper.class, BuildPartnerMapper.class })
public interface BuildPartnerFeesMapper  extends EntityMapper<BuildPartnerFeesDTO, BuildPartnerFees> {

    @Mapping(source = "bpFeeCategory", target = "bpFeeCategoryDTO")
    @Mapping(source = "bpFeeFrequency", target = "bpFeeFrequencyDTO")
    @Mapping(source = "bpFeeCurrency", target = "bpFeeCurrencyDTO")
    @Mapping(source = "bpAccountType", target = "bpAccountTypeDTO")
    @Mapping(source = "buildPartner", target = "buildPartnerDTO")
    BuildPartnerFeesDTO toDto(BuildPartnerFees buildPartnerFees);

    @Mapping(source = "bpFeeCategoryDTO", target = "bpFeeCategory")
    @Mapping(source = "bpFeeFrequencyDTO", target = "bpFeeFrequency")
    @Mapping(source = "bpFeeCurrencyDTO", target = "bpFeeCurrency")
    @Mapping(source = "bpAccountTypeDTO", target = "bpAccountType")
    @Mapping(source = "buildPartnerDTO", target = "buildPartner")
    BuildPartnerFees toEntity(BuildPartnerFeesDTO buildPartnerFeesDTO);
}
