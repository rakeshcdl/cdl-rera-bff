package com.cdl.escrow.service;

import com.cdl.escrow.dto.ApplicationUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationUserService {
    Page<ApplicationUserDTO> getAllApplicationUser(final Pageable pageable);

    Optional<ApplicationUserDTO> getApplicationUserById(Long id);

    ApplicationUserDTO saveApplicationUser(ApplicationUserDTO applicationUserDTO);

    ApplicationUserDTO updateApplicationUser(Long id, ApplicationUserDTO applicationUserDTO);

    Boolean deleteApplicationUserById(Long id);
}
