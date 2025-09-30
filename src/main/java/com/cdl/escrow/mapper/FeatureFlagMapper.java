package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.FeatureFlagDTO;
import com.cdl.escrow.entity.FeatureFlag;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeatureFlagMapper extends EntityMapper<FeatureFlagDTO, FeatureFlag> {

    FeatureFlagDTO toDto(FeatureFlag featureFlag);
    FeatureFlag toEntity(FeatureFlagDTO featureFlagDTO);
}
