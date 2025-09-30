package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Types;
import java.time.ZonedDateTime;
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
@Table(name = "workflow_request_log")
public class WorkflowRequestLog implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_request_log_id_seq_gen",
            sequenceName = "workflow_request_log_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_request_log_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowRequest workflowRequest;

    private String eventType;

    private String eventByUser;

    private String eventByGroup;

    @CreationTimestamp
    private ZonedDateTime eventAt;

   /* @Column(columnDefinition = "jsonb")
    private String detailsJson;*/

    @Column(name = "details_json", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> detailsJson;

    private Boolean enabled ;

    private Boolean deleted;
}
