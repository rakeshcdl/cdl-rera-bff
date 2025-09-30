/**
 * AppLanguageTranslationServiceImpl.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 22/07/25
 */


package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import com.cdl.escrow.entity.AppLanguageTranslation;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.AppLanguageTranslationMapper;
import com.cdl.escrow.repository.AppLanguageTranslationRepository;
import com.cdl.escrow.service.AppLanguageTranslationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppLanguageTranslationServiceImpl implements AppLanguageTranslationService {

    private final AppLanguageTranslationRepository repository;
    private final AppLanguageTranslationMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<AppLanguageTranslationDTO> getAllAppLanguageTranslation(Pageable pageable) {
        log.debug("Fetching all application configurations translation, page: {}", pageable.getPageNumber());
        Page<AppLanguageTranslation> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppLanguageTranslationDTO> getAppLanguageTranslationById(Long id) {
        log.debug("Fetching application configuration translation with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }


    @Override
    @Transactional
    public AppLanguageTranslationDTO saveAppLanguageTranslation(AppLanguageTranslationDTO appLanguageTranslationDTO) {
        log.info("Saving new application configuration translation");
        AppLanguageTranslation entity = mapper.toEntity(appLanguageTranslationDTO);
        log.info("App language translation json::"+ entity);
        AppLanguageTranslation saved = repository.save(entity);
        return mapper.toDto(saved);
    }


    @Override
    @Transactional
    public AppLanguageTranslationDTO updateAppLanguageTranslation(Long id, AppLanguageTranslationDTO appLanguageTranslationDTO) {
        log.info("Updating application configuration translation with ID: {}", id);

        AppLanguageTranslation existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Configuration translation not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        AppLanguageTranslation toUpdate = mapper.toEntity(appLanguageTranslationDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        AppLanguageTranslation updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }


    @Override
    @Transactional
    public Boolean deleteAppLanguageTranslationById(Long id) {
        log.info("Deleting application configuration translation with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Configuration translation not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }



    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getAllAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAll();
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getNavMenuAppLanguageTranslations() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("NAV_MENU");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getBuildPartnerAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("BUILD_PARTNER");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getBuildPartnerAssestAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("BUILD_PARTNER_ASSEST");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getCapitalPartnerAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("CAPITAL_PARTNER");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getTransactionsAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("TRANSACTIONS");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getPaymentsAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("PAYMENTS");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getSuretyBondAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("SURETY_BOND");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public boolean softDeleteAppLanguageTranslationById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getUserdAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("STAKEHOLDER");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getRoleAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("ROLES");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    @Transactional
    public List<AppLanguageTranslationDTO> getSGroupAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("PERMISSIONS");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    public List<AppLanguageTranslationDTO> getFeeRepushAppLanguageTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("FEE_REPUSH");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    public List<AppLanguageTranslationDTO> getReportTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("REPORTS");
        return mapper.toDtoList(appLanguageTranslationList);
    }

    @Override
    public List<AppLanguageTranslationDTO> getDashboardTranslationData() {
        List<AppLanguageTranslation> appLanguageTranslationList = repository.findAllByModuleName("DASHBOARD");
        return mapper.toDtoList(appLanguageTranslationList);
    }
}
