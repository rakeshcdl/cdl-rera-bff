package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "workflow_amount_rule")
public class WorkflowAmountRule implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_amount_rule_id_seq_gen",
            sequenceName = "workflow_amount_rule_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_amount_rule_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowDefinition workflowDefinition;


    private String currency;


    private BigDecimal minAmount;


    private BigDecimal maxAmount;

    private Integer priority ;

    private Integer requiredMakers;

    private Integer requiredCheckers;

    private Boolean isActive;

    private Long workflowId;

    private String amountRuleName;

    @OneToMany(mappedBy = "workflowAmountRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowAmountStageOverride> stageOverrides;
    private Boolean enabled ;

    private Boolean deleted;
}
