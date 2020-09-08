package org.fao.geonet.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * An Id object for {@link MetadataVoteBySession}
 *
 * @author Pete
 */
@Embeddable
@Access(AccessType.PROPERTY)
public class MetadataVoteBySessionId implements Serializable {
    private static final long serialVersionUID = 2793801901686171688L;
    private int _metadataId;
    private String _sessionId;

    /**
     * Default constructor used by JPA framework.
     */
    public MetadataVoteBySessionId() {
        // default constructor for JPA construction.
    }

    /**
     * Convenience constructor.
     *
     * @param metatatId the metadata id that is being rated.
     * @param sessionId the id of the user's session making the vote.
     */
    public MetadataVoteBySessionId(int metatatId, String sessionId) {
        this._metadataId = metatatId;
        this._sessionId = sessionId;
    }

    /**
     * Get the id of the associated metadata.
     *
     * @return the id of the associated metadata.
     */
    @Column(name = "metadataId", nullable = false)
    public int getMetadataId() {
        return _metadataId;
    }

    /**
     * Set the id of the associated metadata.
     *
     * @param metadataId the id of the associated metadata.
     */
    public void setMetadataId(int metadataId) {
        this._metadataId = metadataId;
    }

    /**
     * Get the Session ID of the user the vote is related to.
     *
     * @return the Session ID of the user the vote is related to.
     */
    @Column(name = "sessionId", nullable = false, length = 50)
    public String getSessionId() {
        return _sessionId;
    }

    /**
     * Set the Session ID of the user the vote is related to.
     *
     * @param sessionId the session ID of the user the vote is related to.
     */
    public void setSessionId(String sessionId) {
        this._sessionId = sessionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_sessionId == null) ? 0 : _sessionId.hashCode());
        result = prime * result + _metadataId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetadataVoteBySessionId other = (MetadataVoteBySessionId) obj;
        if (_sessionId == null) {
            if (other._sessionId != null)
                return false;
        } else if (!_sessionId.equals(other._sessionId))
            return false;
        if (_metadataId != other._metadataId)
            return false;
        return true;
    }

}
