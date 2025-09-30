package com.cdl.escrow.serviceimpl;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.mapper.TaskStatusMapper;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskStatusServiceImpl implements TaskStatusService {

    private final TaskStatusRepository repository;

    private final TaskStatusMapper mapper;


    @Override
    public Page<TaskStatusDTO> getAllTaskStatus(Pageable pageable) {
        log.debug("Fetching all task status, page: {}", pageable.getPageNumber());
        Page<TaskStatus> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<TaskStatusDTO> getTaskStatusyId(Long id) {
        log.debug("Fetching task status with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public TaskStatusDTO saveTaskStatus(TaskStatusDTO taskStatusDTO) {
        log.info("Saving new task status");
        TaskStatus entity = mapper.toEntity(taskStatusDTO);
        TaskStatus saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public TaskStatusDTO updateTaskStatus(Long id, TaskStatusDTO taskStatusDTO) {
        log.info("Updating task status with ID: {}", id);

        TaskStatus existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("task status not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        TaskStatus toUpdate = mapper.toEntity(taskStatusDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        TaskStatus updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteTaskStatusById(Long id) {
        log.info("Deleting task status with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("task status not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public boolean softTaskStatusServiceById(Long id) {
        return repository.findByIdAndDeletedFalse(id).map(entity -> {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }).orElse(false);
    }
}
