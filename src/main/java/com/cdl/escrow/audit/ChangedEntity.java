package com.cdl.escrow.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class ChangedEntity {

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "change_type")
    private String changeType;

    // Getters & Setters
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
}
