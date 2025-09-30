


package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "app_language_translation")
public class AppLanguageTranslation implements Serializable {
    @Id
    @SequenceGenerator(
            name = "app_language_translation_id_seq_gen",
            sequenceName = "app_language_translation_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_language_translation_seq_gen"
    )
    private Long id;

    private String configId;

    private String configValue;

    private String content;

    @ManyToOne
    @JsonIgnore
    private ApplicationModule applicationModule;

    @ManyToOne
    @JsonIgnore
    private AppLanguageCode appLanguageCode;

    private Boolean enabled ;

    private Boolean deleted ;

}
