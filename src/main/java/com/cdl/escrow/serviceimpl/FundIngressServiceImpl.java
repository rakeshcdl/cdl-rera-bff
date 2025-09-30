package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.FundIngressDTO;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.FundIngressMapper;
import com.cdl.escrow.repository.FundIngressRepository;
import com.cdl.escrow.service.FundIngressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundIngressServiceImpl implements FundIngressService {

   private final FundIngressRepository repository;

    private final FundIngressMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public Page<FundIngressDTO> getAllFundIngress(Pageable pageable) {
        log.debug("Fetching all capital partner unit , page: {}", pageable.getPageNumber());

        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<FundIngressDTO> getFundIngressById(Long id) {
        log.debug("Fetching Fund Ingress with ID: {}", id);
       return Optional.empty();
    }


    @Override
    @Transactional
    public FundIngressDTO saveFundIngress(FundIngressDTO fundIngressDTO) {
        log.info("Saving new Fund Ingress");
        return null;
    }


    @Override
    @Transactional
    public FundIngressDTO updateFundIngress(Long id, FundIngressDTO fundIngressDTO) {
        log.info("Updating Fund Ingress with ID: {}", id);

        return null;
    }


    @Override
    @Transactional
    public Boolean deleteFundIngressById(Long id) {
        log.info("Deleting fund ingress  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Fund Ingress not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softFundIngressServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}