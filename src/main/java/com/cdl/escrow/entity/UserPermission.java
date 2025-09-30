package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents the permissions granted to a user within the application.
 * This entity defines the actions that a user can or cannot perform, such as access rights to specific resources,
 * roles, or features within the application.
 * Permissions are essential for managing authorization and security.
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
@Table(name = "user_permission")
public class UserPermission implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_permission_id_seq_gen",
            sequenceName = "user_permission_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_permission_id_seq_gen"
    )
    private Long id ;

    private String userId;

    private String permissionType;

    private boolean isGranted;

    private Boolean enabled ;

    private Boolean deleted;
}