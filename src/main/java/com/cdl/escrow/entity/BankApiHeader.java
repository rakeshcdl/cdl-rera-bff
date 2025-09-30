/**
 * BankApiHeader.java
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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "bank_api_header")
public class BankApiHeader implements Serializable {

    @Id
    @SequenceGenerator(
            name = "bank_api_header_id_seq_gen",
            sequenceName = "bank_api_header_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_api_header_id_seq_gen"
    )
    private Long id;

    private String name;

    private String value;

    private Boolean isSensitive;

    @ManyToOne
    @JoinColumn(name = "config_id")
    private BankConfig bankConfig;

    private Boolean enabled ;

    private Boolean deleted;

}
