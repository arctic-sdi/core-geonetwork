/*
 * Copyright (C) 2001-2016 Food and Agriculture Organization of the
 * United Nations (FAO-UN), United Nations World Food Programme (WFP)
 * and United Nations Environment Programme (UNEP)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 *
 * Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
 * Rome - Italy. email: geonetwork@osgeo.org
 */

package org.fao.geonet.domain;

import org.fao.geonet.entitylistener.MetadataVoteByIpEntityListenerManager;

import javax.persistence.*;

/**
 * An entity that tracks which users have voted on metadata. It currently tracks by Ip address so
 * that each IP address can only vote a given metadata once.
 *
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "MetadataVote")
@EntityListeners(MetadataVoteByIpEntityListenerManager.class)
public class MetadataVoteByIp extends GeonetEntity {
    private MetadataVoteByIpId _id;
    private int _vote;

    /**
     * Get the id object of the metadata vote entity.
     *
     * @return the id object of the metadata vote entity.
     */
    @EmbeddedId
    public MetadataVoteByIpId getId() {
        return _id;
    }

    /**
     * Set the id object of the metadata vote entity.
     *
     * @param id the id object of the metadata vote entity.
     */
    public void setId(MetadataVoteByIpId id) {
        this._id = id;
    }

    /**
     * Get the vote for this IP address.
     *
     * @return the vote for this IP address.
     */
    @Column(nullable = false)
    public int getVote() {
        return _vote;
    }

    /**
     * Set the vote for this IP address.
     *
     * @param vote Set the vote for this IP address.
     */
    public void setVote(int vote) {
        this._vote = vote;
    }
}
