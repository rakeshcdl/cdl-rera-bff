package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Represents a document within the application, such as a report, contract,
 * or any other type of file or data associated with the system.
 * The entity stores metadata about the document such as document type,
 * status, and any related files or content.
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
@Table(name = "real_estate_document")
public class RealEstateDocument implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_document_id_seq_gen",
            sequenceName = "real_estate_document_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_document_id_seq_gen"
    )
    private Long id ;

    private String rea;

    private String documentName;

    private String content;

    private String location;

    private String module;

    private String recordId;

    private String storageType;

    private ZonedDateTime uploadDate;

    private String documentSize;

    private ZonedDateTime validityDate;

    private int version;

    @Lob
    @Column(name = "event_detail", columnDefinition = "TEXT")
    private String eventDetail;

    @ManyToOne
    private ApplicationSetting documentType;

    private Boolean enabled ;

    private Boolean deleted;
}
