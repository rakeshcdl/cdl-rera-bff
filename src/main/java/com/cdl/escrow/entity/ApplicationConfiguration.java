package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents the configuration of the application.
 * This entity stores critical configurations such as database settings,
 * API keys, third-party integrations, and other configurations that are essential
 * for the application's initialization and runtime behavior.
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
@Table(name = "application_configuration")
public class ApplicationConfiguration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_cfg_id_seq_gen")
    @SequenceGenerator(name = "app_cfg_id_seq_gen", sequenceName = "application_configuration_id_seq"
            , allocationSize = 50)

    private Long id ;

    private String configKey;

    /*@Column(name = "config_value", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)*/
    private String configValue;


    private Boolean enabled ;

    private Boolean deleted;


}