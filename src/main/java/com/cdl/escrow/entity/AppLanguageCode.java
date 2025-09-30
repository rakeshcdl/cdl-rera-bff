/**
 * LanguageCode.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 26/05/25
 */


package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "app_language_code")
public class AppLanguageCode implements Serializable {

    @Id
    @SequenceGenerator(
            name = "app_language_code_id_seq_gen",
            sequenceName = "app_language_code_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_language_code_seq_gen"
    )
    private Long id;

    private String languageCode; // ISO code, e.g., 'en'

    private String nameKey; // 'English'

    private String nameNativeValue; // 'English', 'Français', 'Deutsch'

    private boolean enabled;

    private boolean isRtl;

    @OneToMany(mappedBy = "appLanguageCode")
    @JsonIgnore
    private Set<AppLanguageTranslation> languageTranslations ;

    private Boolean deleted ;

}
