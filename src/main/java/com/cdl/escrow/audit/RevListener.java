package com.cdl.escrow.audit;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionType;

import java.io.Serializable;
import java.util.Map;

public  class RevListener implements EntityTrackingRevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        RevInfo revInfo = (RevInfo) revisionEntity;
        /*revInfo.setUsername(AuditContext.getUsername());
        revInfo.setIpAddress(AuditContext.getIpAddress());
        revInfo.setUserAgent(AuditContext.getUserAgent());
        revInfo.setRequestId(AuditContext.getRequestId());*/
    }


    @Override
    public void entityChanged(Class aClass, String s, Object o, RevisionType revisionType, Object o1) {

    }
}