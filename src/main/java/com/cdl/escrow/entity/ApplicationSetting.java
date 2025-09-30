package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Set;

/**
 * Represents the settings or options available in the application.
 * It stores configuration values such as feature flags, app preferences,
 * or other essential settings that determine the behavior of the application.
 * These settings are usually loaded once during startup and can be updated via administrative interfaces.
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
@Table(name = "application_setting")
public class ApplicationSetting implements Serializable {

    @Id
    @SequenceGenerator(
            name = "application_setting_id_seq_gen",
            sequenceName = "application_setting_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_setting_id_seq_gen"
    )
    private Long id ;

    private String settingKey;

    private String settingValue;

    @ManyToOne
    @JsonIgnore
    private AppLanguageTranslation languageTranslation;

    @Lob
    @Column(name = "remarks", columnDefinition = "TEXT")

    private String remarks;

    private Boolean enabled ;

    private Boolean deleted ;


    public ApplicationSetting(Long id) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationSetting that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
