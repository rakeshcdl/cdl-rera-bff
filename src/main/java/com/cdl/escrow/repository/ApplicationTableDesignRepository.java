package com.cdl.escrow.repository;


import com.cdl.escrow.entity.ApplicationTableDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTableDesignRepository extends JpaRepository<ApplicationTableDesign,Long> , JpaSpecificationExecutor<ApplicationTableDesign> {
}
