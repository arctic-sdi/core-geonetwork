package org.fao.geonet.domain;

import org.fao.geonet.entitylistener.MetadataVoteByIpEntityListenerManager;
import org.fao.geonet.entitylistener.MetadataVoteBySessionEntityListenerManager;

import javax.persistence.*;

/**
 * An entity that tracks which users have voted on metadata. It currently tracks by Ip address so
 * that each IP address can only vote a given metadata once.
 *
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "MetadataVote")
@EntityListeners(MetadataVoteBySessionEntityListenerManager.class)
public class MetadataVoteBySession {
    private MetadataVoteBySessionId _id;
    private int _vote;
    private String _username;
    private String _comment;
    private String _ipAddress;
    private String _timestamp;

    /**
     * Get the id object of the metadata vote entity.
     *
     * @return the id object of the metadata vote entity.
     */
    @EmbeddedId
    public MetadataVoteBySessionId getId() {
        return _id;
    }

    /**
     * Set the id object of the metadata vote entity.
     *
     * @param id the id object of the metadata vote entity.
     */
    public void setId(MetadataVoteBySessionId id) {
        this._id = id;
    }

    /**
     * Get the vote for this Session ID.
     *
     * @return the vote for this Session ID.
     */
    @Column(nullable = false)
    public int getVote() {
        return _vote;
    }

    /**
     * Set the vote for this Session ID.
     *
     * @param vote Set the vote for this Session ID.
     */
    public void setVote(int vote) {
        this._vote = vote;
    }

    /**
     * Set the username for this Session ID.
     *
     * @param username Set the username for this Session ID.
     */
    public void setUserName(String username) { this._username = username; }

    /**
     * Get the username for this Session ID.
     *
     * @return the username for this Session ID.
     */
    public String getUserName() {return _username; }

    /**
     * Set the comment for this Session ID.
     *
     * @param comment Set the comment for this Session ID.
     */
    public void setComment(String comment) { this._comment = comment; }

    /**
     * Get the comment for this Session ID.
     *
     * @return the comment for this Session ID.
     */
    public String getComment() { return _comment; }

    /**
     * Set the IP Address for this Session ID.
     *
     * @param ipaddress Set the IP Address for this Session ID.
     */
    public void setIpAddress (String ipaddress) { this._ipAddress = ipaddress; }

    /**
     * Get the IP Address for this Session ID.
     *
     * @return the IP Address for this Session ID.
     */
    public String getIpAddress() { return _ipAddress; }

    /**
     * Set the timestamp for this Session ID.
     *
     * @param timestamp Set the time stamp for this Session ID.
     */
    public void setTimestamp(String timestamp) { this._timestamp = timestamp; }

    /**
     * Get the time stamp for this Session ID.
     *
     * @return the time stamp for this Session ID.
     */
    public String getTimestamp() { return _timestamp; }

}


