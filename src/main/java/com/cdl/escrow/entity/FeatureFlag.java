package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a feature flag used to toggle certain features or functionality in the application.
 * This entity allows for enabling or disabling features at runtime without modifying the application code.
 * It is commonly used for A/B testing, gradual rollouts, or enabling experimental features.
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
@Table(name = "feature_flag")
public class FeatureFlag implements Serializable {
    @Id
    @SequenceGenerator(
            name = "feature_flag_id_seq_gen",
            sequenceName = "feature_flag_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "feature_flag_id_seq_gen"
    )
    private Long id ;

    private String featureName;

    @Lob
    @Column(name = "feature_description", columnDefinition = "TEXT")
    private String featureDescription;

    private Boolean enabled ;

    private Boolean deleted;
}