package com.cdl.escrow.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "revinfo")
@RevisionEntity(RevListener.class)
public class RevInfo extends DefaultRevisionEntity {

        @Column(name = "username")
        private String username;

        @Column(name = "ip_address")
        private String ipAddress;

        @Column(name = "user_agent")
        private String userAgent;

        @Column(name = "request_id")
        private String requestId;

        @ElementCollection
        @CollectionTable(
                name = "rev_changed_entities",
                joinColumns = @JoinColumn(name = "rev_id")
        )
        private List<ChangedEntity> changedEntities = new ArrayList<>();
}
