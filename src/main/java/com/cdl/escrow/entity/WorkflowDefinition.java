package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Defines the workflow configuration for a specific module or transaction type.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "work_flow_definition")
public class WorkflowDefinition implements Serializable {
    @Id
    @SequenceGenerator(
            name = "work_flow_definition_id_seq_gen",
            sequenceName = "work_flow_definition_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "work_flow_definition_id_seq_gen"
    )
    private Long id;

    private String name;

    @ManyToOne
    private ApplicationModule applicationModule;

    @ManyToOne
    private WorkflowAction workflowAction;

    private Integer version = 1;

    private Boolean isActive ;

    private String createdBy;

    private Boolean amountBased;   // true if workflow depends on amount ranges

    private String moduleCode;

    private String actionCode;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "workflowDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowStageTemplate> workflowStageTemplates;

    @OneToMany(mappedBy = "workflowDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowAmountRule> workflowAmountRules;

    private Boolean enabled ;

    private Boolean deleted;
}