package com.cdl.escrow.service;

import com.cdl.escrow.dto.FeatureFlagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FeatureFlagService {
    Page<FeatureFlagDTO> getAllFeatureFlag(final Pageable pageable);

    Optional<FeatureFlagDTO> getFeatureFlagById(Long id);

    FeatureFlagDTO saveFeatureFlag(FeatureFlagDTO featureFlagDTO);

    FeatureFlagDTO updateFeatureFlag(Long id, FeatureFlagDTO featureFlagDTO);

    Boolean deleteFeatureFlagById(Long id);

    boolean softFeatureFlagServiceById(Long id);
}
