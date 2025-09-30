package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "workflow_request")
public class WorkflowRequest implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_request_id_seq_gen",
            sequenceName = "workflow_request_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_request_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowDefinition workflowDefinition;

    private String referenceId;

    private String referenceType;


    private String moduleName;


    private String actionKey;

    private BigDecimal amount;

    private String currency;

   /* @JdbcTypeCode(Types.OTHER)
    @Column(columnDefinition = "jsonb", nullable = false)
    private String payloadJson; // Store entire module request payload*/

    @Column(name = "payload_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> payloadJson;


    private Integer currentStageOrder;


    private String createdBy;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime lastUpdatedAt;

    private Long version;

    @OneToMany(mappedBy = "workflowRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowRequestStage> workflowRequestStages;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;

    private Boolean enabled ;

    private Boolean deleted;
}
