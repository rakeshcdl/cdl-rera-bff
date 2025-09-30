package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.criteria.PendingFundIngressCriteria;
import com.cdl.escrow.criteriaservice.PendingFundIngressCriteriaService;
import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.dto.PendingFundIngressExtDTO;
import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.dto.SplitDataDTO;
import com.cdl.escrow.entity.CapitalPartner;
import com.cdl.escrow.entity.PendingFundIngress;
import com.cdl.escrow.entity.ProcessedFundIngress;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.mapper.PendingFundIngressMapper;
import com.cdl.escrow.repository.PendingFundIngressRepository;
import com.cdl.escrow.service.PendingFundIngressService;
import com.cdl.escrow.service.ProcessedFundIngressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PendingFundIngressServiceImpl implements PendingFundIngressService {

    private final PendingFundIngressRepository repository;

    private final PendingFundIngressMapper mapper;

    private final ProcessedFundIngressService processedFundIngressService;

    private PendingFundIngressCriteriaService pendingFundIngressCriteriaService;

    @Override
    @Transactional(readOnly = true)
    public Page<PendingFundIngressDTO> getAllPendingFundIngress(Pageable pageable) {
        log.debug("Fetching all Pending Fund Egress , page: {}", pageable.getPageNumber());
        Page<PendingFundIngress> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PendingFundIngressDTO> getPendingFundIngressById(Long id) {
        log.debug("Fetching Pending Fund Egress with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public PendingFundIngressDTO savePendingFundIngress(PendingFundIngressDTO pendingFundIngressDTO) {
        log.info("Saving new Pending Fund Egress");
        PendingFundIngress entity = mapper.toEntity(pendingFundIngressDTO);
        PendingFundIngress saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public PendingFundIngressDTO updatePendingFundIngress(Long id, PendingFundIngressDTO pendingFundIngressDTO) {
        log.info("Updating Pending Fund Egress with ID: {}", id);

        PendingFundIngress existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("Pending Fund Egress unit not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        PendingFundIngress toUpdate = mapper.toEntity(pendingFundIngressDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        PendingFundIngress updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    @Transactional
    public Boolean deletePendingFundIngressById(Long id) {
        log.info("Deleting Pending Fund Egress  with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("Pending Fund Egress not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean softPendingFundIngressServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }

    @Override
    public void finalizeDeposit(Long moduleId, TaskStatus status) {

        PendingFundIngress pendingFundIngress =  repository.findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("Pending Fund Ingress not found: " + moduleId));

        pendingFundIngress.setTaskStatus(status);
        repository.save(pendingFundIngress);

    }

    @Override
    public PendingFundIngressExtDTO updateSplitData(PendingFundIngressExtDTO pendingFundIngressExtDTO) {
        String transactionId = pendingFundIngressExtDTO.getPtfiTransactionId();

        List<ProcessedFundIngressDTO> toProcessReconTransactions = new ArrayList<>();

        // get non recon transaction dtos
        LongFilter nonReconIdFilter = new LongFilter();
        nonReconIdFilter.setEquals(pendingFundIngressExtDTO.getId());
        PendingFundIngressCriteria nonReconTCriteria = new PendingFundIngressCriteria();
        nonReconTCriteria.setId(nonReconIdFilter);
        List<PendingFundIngressDTO> nonReconTransactionDtos = this.pendingFundIngressCriteriaService
                .findByListCriteria(nonReconTCriteria);
        PendingFundIngressDTO nonReconTransactionDto = nonReconTransactionDtos.get(0);

        // create all split transaction to recon transaction
        for (SplitDataDTO splitDataDTOExt : pendingFundIngressExtDTO.getSplitData()) {

            // delete transaction
            if (splitDataDTOExt != null && splitDataDTOExt.getIsDeleted() != null && splitDataDTOExt.getIsDeleted()
                    && splitDataDTOExt.getId() != null && splitDataDTOExt.getId() != 0) {
                processedFundIngressService.deleteProcessedFundIngressById(splitDataDTOExt.getId());
            } else if (splitDataDTOExt.getId() != null && splitDataDTOExt.getId() != 0) {
                // update transaction
                Optional<ProcessedFundIngressDTO> reconOptionalDto = this.processedFundIngressService
                        .getProcessedFundIngressById(splitDataDTOExt.getId());
                ProcessedFundIngressDTO reconUpdateDto = reconOptionalDto.get();
                reconUpdateDto = getReconDtos(reconUpdateDto, pendingFundIngressExtDTO, splitDataDTOExt);
                this.processedFundIngressService.saveProcessedFundIngress(reconUpdateDto);
            } else {
                ProcessedFundIngressDTO reconTransactionDTO = new ProcessedFundIngressDTO();
                reconTransactionDTO = getReconDtos(reconTransactionDTO, pendingFundIngressExtDTO, splitDataDTOExt);
                reconTransactionDTO.setPendingFundIngressDTO(nonReconTransactionDto);
                toProcessReconTransactions.add(reconTransactionDTO);
            }

        }

        for (ProcessedFundIngressDTO reconTransactionDTO : toProcessReconTransactions) {
            processedFundIngressService.saveProcessedFundIngress(reconTransactionDTO);
        }


        this.savePendingFundIngress(nonReconTransactionDto);

        // Here we have to set the workflow request

        return null;
    }

    private ProcessedFundIngressDTO getReconDtos(ProcessedFundIngressDTO reconTransactionDTO,
                                             PendingFundIngressExtDTO nonReconTransactionDTOExt, SplitDataDTO splitDataDTOExt) {
        reconTransactionDTO.setPfiAmount(splitDataDTOExt.getAmount());
        reconTransactionDTO.setBucketTypeDTO(splitDataDTOExt.getBucketType());
        reconTransactionDTO.setBucketSubTypeDTO(splitDataDTOExt.getBucketSubType());
        reconTransactionDTO.setPfiIsAllocated(false);
        // parent records
        reconTransactionDTO.setPfiTransactionId(nonReconTransactionDTOExt.getPtfiTransactionId());
       // reconTransactionDTO.setBankAccount(nonReconTransactionDTOExt.getBankAccount());
        reconTransactionDTO.setPfiDescription(nonReconTransactionDTOExt.getPtfiDescription());
        reconTransactionDTO.setPfiNarration(nonReconTransactionDTOExt.getPtfiNarration());
        reconTransactionDTO.setRealEstateAssestDTO(nonReconTransactionDTOExt.getRealEstateAssestDTO());
        reconTransactionDTO.setPfiSpecialField1(splitDataDTOExt.getTasRefNumber());
        reconTransactionDTO.setPfiTransactionDate(nonReconTransactionDTOExt.getPtfiTransactionDate());
        reconTransactionDTO.setCapitalPartnerUnitDTO(
                splitDataDTOExt.getUnit() != null ? splitDataDTOExt.getUnit() : nonReconTransactionDTOExt.getCapitalPartnerUnitDTO());
        reconTransactionDTO.setPfiUpdateTas(
                nonReconTransactionDTOExt.getPtfiTasUpdate() != null ? nonReconTransactionDTOExt.getPtfiTasUpdate() : false);
        reconTransactionDTO.setPfiTasUpdate(
                nonReconTransactionDTOExt.getPtfiTasUpdate() != null ? nonReconTransactionDTOExt.getPtfiTasUpdate() : false);
       // reconTransactionDTO.setPfiCreditedRetention(nonReconTransactionDTOExt.getPt());

       /* if (nonReconTransactionDTOExt.getPtfiR()
                && splitDataDTOExt.getBucketType().getSettingKey().equals("BT_UNIT_HOLDER_DEPOSIT")) {
            Double splitAmt = reconTransactionDTO.getPfiAmount() * 5 / 100;
            // reconTransactionDTO.setAmount(reconTransactionDTO.getAmount());
            reconTransactionDTO.setRetentionAmount(splitAmt);
        }*/
        reconTransactionDTO.setPfiTotalAmount(reconTransactionDTO.getPfiAmount());
        reconTransactionDTO.setPfiUnitRefNumber(nonReconTransactionDTOExt.getPtfiUnitRefNumber());
        reconTransactionDTO.setDepositModeDTO(splitDataDTOExt.getDepositMode());
        if (null != splitDataDTOExt.getCheckNumber()) {
            reconTransactionDTO.setPfiCheckNumber(splitDataDTOExt.getCheckNumber());
        }
        if (null != nonReconTransactionDTOExt.getPtfiRetentionAmount()) {
            reconTransactionDTO.setPfiRetentionAmount(nonReconTransactionDTOExt.getPtfiRetentionAmount());
        }
        reconTransactionDTO.setPfiPrimaryUnitHolderName(nonReconTransactionDTOExt.getPtfiPrimaryUnitHolderName());
        return reconTransactionDTO;
    }
}
