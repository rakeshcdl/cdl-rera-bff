package com.cdl.escrow.service;

import com.cdl.escrow.dto.ApplicationTableDesignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationTableDesignService {
    Page<ApplicationTableDesignDTO> getAllApplicationTableDesign(final Pageable pageable);

    Optional<ApplicationTableDesignDTO> getApplicationTableDesignById(Long id);

    ApplicationTableDesignDTO saveApplicationTableDesign(ApplicationTableDesignDTO applicationTableDesignDTO);

    ApplicationTableDesignDTO updateApplicationTableDesign(Long id, ApplicationTableDesignDTO applicationTableDesignDTO);

    Boolean deleteApplicationTableDesignById(Long id);
}
