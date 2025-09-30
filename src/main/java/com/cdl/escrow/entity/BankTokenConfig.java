
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
@Table(name = "bank_token_config")
public class BankTokenConfig implements Serializable {

    @Id
    @SequenceGenerator(
            name = "bank_token_config_id_seq_gen",
            sequenceName = "bank_token_config_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_token_config_id_seq_gen"
    )
    private Long id;

    private String tokenUrl;

    private String method;

    @Column(columnDefinition = "TEXT")
    private String headersJson;

    @Column(columnDefinition = "TEXT")
    private String bodyTemplate;

    private String jsonPath;

    @OneToOne
    @JoinColumn(name = "config_id")
    private BankConfig bankConfig;

    private Boolean enabled ;

    private Boolean deleted;

}
