package org.fao.geonet.repository;

import org.fao.geonet.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public class MetadataVoteBySessionRepositoryImpl implements MetadataVoteBySessionRepositoryCustom {

    @PersistenceContext
    private EntityManager _entityManager;

    @Override
    public int averageVote(int metadataId) {
        final CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cbQuery = cb.createQuery(Double.class);
        Root<MetadataVoteBySession> root = cbQuery.from(MetadataVoteBySession.class);

        Expression<Double> mean = cb.avg(root.get(MetadataVoteBySession_.vote));
        cbQuery.select(mean);
        cbQuery.where(cb.equal(root.get(MetadataVoteBySession_.id).get(MetadataVoteBySessionId_.metadataId), metadataId));
        return _entityManager.createQuery(cbQuery).getSingleResult().intValue();
    }

    @Override
    public int sumVote(int metadataId) {
        final CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> cbQuery = cb.createQuery(Integer.class);
        Root<MetadataVoteBySession> root = cbQuery.from(MetadataVoteBySession.class);

        Expression<Integer> sum = cb.sum(root.get(MetadataVoteBySession_.vote));
        cbQuery.select(sum);
        cbQuery.where(cb.equal(root.get(MetadataVoteBySession_.id).get(MetadataVoteBySessionId_.metadataId), metadataId));
        return _entityManager.createQuery(cbQuery).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public int deleteAllById_MetadataId(int metadataId) {
        String entityType = MetadataVoteBySession.class.getSimpleName();
        String metadataIdPropName = MetadataVoteBySessionId_.metadataId.getName();
        Query query = _entityManager.createQuery("DELETE FROM " + entityType + " WHERE " + metadataIdPropName + " = " + metadataId);
        return query.executeUpdate();
    }
}
