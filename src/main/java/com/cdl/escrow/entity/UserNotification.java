package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a notification sent to a user within the application.
 * This entity holds information about the notification type, recipient, content,
 * and status (e.g., read/unread).
 * Notifications are used for communication with users, such as alerts, messages, or updates.
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
@Table(name = "user_notification")
public class UserNotification implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_notification_id_seq_gen",
            sequenceName = "user_notification_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_notification_id_seq_gen"
    )
    private Long id ;

    private String notificationId;

    private String userId;

    private String content;

    private boolean isRead;

    private Boolean enabled ;

    private Boolean deleted;
}