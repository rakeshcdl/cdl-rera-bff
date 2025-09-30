/**
 * BankConfig.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 23/07/25
 */


package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
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
@Table( name = "bank_config")
public class BankConfig implements Serializable {

    @Id
    @SequenceGenerator(
            name = "bank_config_id_seq_gen",
            sequenceName = "bank_config_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_config_id_seq_gen"
    )
    private Long id;

    private String bankCode;

    private String apiName;

    private String apiUrl;

    private String fallbackUrl; // New: Backup API
    private Integer retryCount;   // New: Number of retries
    private Integer retryDelay;   // New: Delay in seconds (base for exponential backoff)


    private String httpMethod;

    @Column(columnDefinition = "TEXT")
    private String bodyTemplate; // In JSON Format

    private Boolean encryptionEnabled;

    private String encryptedFields; // comma-separated, we can add multipls fields here

    private Boolean retryEnabled;

    @Column(columnDefinition = "TEXT")
    private String fallbackMessage;

    @OneToMany(mappedBy = "bankConfig", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<BankApiHeader> headers;

    @OneToMany(mappedBy = "bankConfig", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  List<BankApiParameter> parameters;

    @OneToOne(mappedBy = "bankConfig", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private  BankTokenConfig tokenConfig;
    private Boolean enabled ;

    private Boolean deleted;


}
