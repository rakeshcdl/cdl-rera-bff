package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a session for a logged-in user in the application.
 * This entity tracks user activity, session state, login history, and session expiration.
 * It is crucial for handling authentication and maintaining state during a user's interaction with the system.
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
@Table(name = "user_session")
public class UserSession implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_session_id_seq_gen",
            sequenceName = "user_session_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_session_id_seq_gen"
    )
    private Long id ;

    private String sessionId;

    private String userId;

    private long createdAt;

    private long lastAccessedAt;

    private Boolean enabled ;

    private Boolean deleted;
}
