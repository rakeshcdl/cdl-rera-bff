package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Represents a fee associated with a Real Estate Asset,
 * such as government registration, administrative, or service fees.
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
@Table(name = "real_estate_assest_fee")
public class RealEstateAssestFee implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_fee_id_seq_gen",
            sequenceName = "real_estate_assest_fee_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_fee_id_seq_gen"
    )
    private Long id;

    private Double reafAmount;

    private Double reafDebitAmount;

    private Double reafTotalAmount;

    private ZonedDateTime reafCalender;

    private ZonedDateTime reafCollectionDate;

    private ZonedDateTime reafNextRecoveryDate;

    private Double reafVatPercentage;

    private Boolean reafCollected;

    private Double reafFeePercentage;



    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reafCategory;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reafCurrency;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reafFrequency;

    @ManyToOne
    private ApplicationSetting reafAccountType;


    @OneToMany(mappedBy = "realEstateAssestFee")
    @JsonIgnore
    private Set<RealEstateAssestFeeHistory> realEstateAssestFeeHistories ;

    private Boolean enabled ;

    private Boolean deleted;
}
