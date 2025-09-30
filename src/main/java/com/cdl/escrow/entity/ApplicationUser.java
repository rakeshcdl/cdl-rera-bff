package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a user of the application.
 * This entity contains information about the user such as username, email,
 * roles, and other personal data required for user management.
 * It plays a key role in authentication, authorization, and user profile management.
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
@Table(name = "application_user")
public class ApplicationUser implements Serializable {

    @Id
    @SequenceGenerator(
            name = "application_user_id_seq_gen",
            sequenceName = "application_user_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_user_id_seq_gen"
    )
    private Long id ;

    private String username;
    private String password;
    private String email;

    private Boolean deleted ;

}