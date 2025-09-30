package com.cdl.escrow.service;

import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.dto.record.BuildPartnerRecord;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuildPartnerService {
    Page<BuildPartnerDTO> getAllBuildPartner(final Pageable pageable);

    Optional<BuildPartnerDTO> getBuildPartnerById(Long id);

    BuildPartnerDTO saveBuildPartner(BuildPartnerDTO buildPartnerDTO);

    BuildPartnerDTO updateBuildPartner(Long id, BuildPartnerDTO buildPartnerDTO);

    Boolean deleteBuildPartnerById(Long id);

    void finalizeBuildPartner(Long moduleId, TaskStatus status);

    boolean softBuildPartnerContactServiceById(Long id);

    //Optional<BuildPartnerRecord> getBuildPartnerDataById(Long id);
}
