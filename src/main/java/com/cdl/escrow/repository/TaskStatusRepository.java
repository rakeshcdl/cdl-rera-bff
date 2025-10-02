package com.cdl.escrow.repository;

import com.cdl.escrow.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus,Long>, JpaSpecificationExecutor<TaskStatus> {

    Optional<TaskStatus> findByIdAndDeletedFalse(Long id);

    Optional<TaskStatus> findByName(String inProgress);

    Optional<TaskStatus> findByCode(String pending);
}

