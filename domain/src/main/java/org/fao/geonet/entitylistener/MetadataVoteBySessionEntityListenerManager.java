package org.fao.geonet.entitylistener;

import org.fao.geonet.domain.MetadataVoteBySession;

import javax.persistence.*;

public class MetadataVoteBySessionEntityListenerManager  extends AbstractEntityListenerManager<MetadataVoteBySession> {
    @PrePersist
    public void prePresist(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PrePersist, entity);
    }

    @PreRemove
    public void preRemove(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PreRemove, entity);
    }

    @PostPersist
    public void postPersist(final MetadataVoteBySession entity) { handleEvent(PersistentEventType.PostPersist, entity);}

    @PostRemove
    public void postRemove(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PostRemove, entity);
    }

    @PreUpdate
    public void preUpdate(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PreUpdate, entity);
    }

    @PostUpdate
    public void postUpdate(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PostUpdate, entity);
    }

    @PostLoad
    public void postLoad(final MetadataVoteBySession entity) {
        handleEvent(PersistentEventType.PostLoad, entity);
    }

}
