package org.fao.geonet.repository;

import org.fao.geonet.domain.MetadataVoteBySession;
import org.fao.geonet.domain.MetadataVoteBySessionId;

import java.util.List;

public interface MetadataVoteBySessionRepository extends GeonetRepository<MetadataVoteBySession, MetadataVoteBySessionId>,
    MetadataVoteBySessionRepositoryCustom {
    /**
     * Find all the ratings for the given Metadata.
     *
     * @param metadataId id of metadata.
     */
    List<MetadataVoteBySession> findAllByIdMetadataId(int metadataId);
}
