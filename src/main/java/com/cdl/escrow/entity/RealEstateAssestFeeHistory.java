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
 * Maintains the historical record of fee changes or payments for a Real Estate Asset.
 * Useful for auditing and financial tracking.
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
@Table(name = "real_estate_assest_fee_history")
public class RealEstateAssestFeeHistory implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_fee_history_id_seq_gen",
            sequenceName = "real_estate_assest_fee_history_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_fee_history_id_seq_gen"
    )
    private Long id;

    private Double reafhAmount;

    private Double reafhTotalAmount;

    private Double reafhVatPercentage;

    private ZonedDateTime reafhTransactionDate;

    private Boolean reafhSuccess;

    private Boolean reafhStatus;

    private String reahfRemark;


    @Column(name = "reafh_fee_response", columnDefinition = "TEXT")
    private String reafhFeeResponse;

    private String reafhResponseStatus;

    private String reafhSpecialField1;

    private String reafhSpecialField2;

    private String reafhSpecialField3;

    private String reafhSpecialField4;

    private String reafhSpecialField5;


    @Column(name = "reafh_fee_request_body", columnDefinition = "TEXT")
    private String reafhFeeRequestBody;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssestFee realEstateAssestFee;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    private Boolean enabled ;

    private Boolean deleted;

}
