package com.cdl.escrow.service;


import com.cdl.escrow.dto.ApplicationFormDesignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationFormDesignService {
    Page<ApplicationFormDesignDTO> getAllApplicationFormDesign(final Pageable pageable);

    Optional<ApplicationFormDesignDTO> getApplicationFormDesignById(Long id);

    ApplicationFormDesignDTO saveApplicationFormDesign(ApplicationFormDesignDTO applicationFormDesignDTO);

    ApplicationFormDesignDTO updateApplicationFormDesign(Long id, ApplicationFormDesignDTO applicationFormDesignDTO);

    Boolean deleteApplicationFormDesignById(Long id);

    boolean softDeleteApplicationFormDesignById(Long id);
}
