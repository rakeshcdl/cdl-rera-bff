package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.ApplicationUserDTO;
import com.cdl.escrow.entity.ApplicationUser;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationUserMapper extends EntityMapper<ApplicationUserDTO, ApplicationUser> {

    ApplicationUserDTO toDto(ApplicationUser applicationUser);
    ApplicationUser toEntity(ApplicationUserDTO applicationUserDTO);
}
