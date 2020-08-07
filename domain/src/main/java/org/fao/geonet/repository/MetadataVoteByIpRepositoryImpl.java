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

package org.fao.geonet.repository;

import org.fao.geonet.domain.MetadataVoteByIp;
import org.fao.geonet.domain.MetadataVoteByIpId_;
import org.fao.geonet.domain.MetadataVoteByIp_;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Implementation for MetadataVoteByIpRepositoryCustom interface.
 * <p/>
 * User: jeichar Date: 9/5/13 Time: 4:15 PM
 */
public class MetadataVoteByIpRepositoryImpl implements MetadataVoteByIpRepositoryCustom {

    @PersistenceContext
    private EntityManager _entityManager;

    @Override
    public int averageVote(final int metadataId) {
        final CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cbQuery = cb.createQuery(Double.class);
        Root<MetadataVoteByIp> root = cbQuery.from(MetadataVoteByIp.class);

        Expression<Double> mean = cb.avg(root.get(MetadataVoteByIp_.vote));
        cbQuery.select(mean);
        cbQuery.where(cb.equal(root.get(MetadataVoteByIp_.id).get(MetadataVoteByIpId_.metadataId), metadataId));
        return _entityManager.createQuery(cbQuery).getSingleResult().intValue();
    }

    @Override
    public int sumVote(int metadataId) {
        final CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cbQuery = cb.createQuery(Integer.class);
        Root<MetadataVoteByIp> root = cbQuery.from(MetadataVoteByIp.class);

        Expression<Integer> sum = cb.sum(root.get(MetadataVoteByIp_.vote));
        cbQuery.select(sum);
        cbQuery.where(cb.equal(root.get(MetadataVoteByIp_.id).get(MetadataVoteByIpId_.metadataId), metadataId));
        return _entityManager.createQuery(cbQuery).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public int deleteAllById_MetadataId(final int metadataId) {
        String entityType = MetadataVoteByIp.class.getSimpleName();
        String metadataIdPropName = MetadataVoteByIpId_.metadataId.getName();
        Query query = _entityManager.createQuery("DELETE FROM " + entityType + " WHERE " + metadataIdPropName + " = " + metadataId);
        return query.executeUpdate();
    }
}
