package grafismo.repository;

import grafismo.domain.Broadcast;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BroadcastRepositoryWithBagRelationshipsImpl implements BroadcastRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Broadcast> fetchBagRelationships(Optional<Broadcast> broadcast) {
        return broadcast.map(this::fetchBroadcastPersonnelMembers);
    }

    @Override
    public Page<Broadcast> fetchBagRelationships(Page<Broadcast> broadcasts) {
        return new PageImpl<>(fetchBagRelationships(broadcasts.getContent()), broadcasts.getPageable(), broadcasts.getTotalElements());
    }

    @Override
    public List<Broadcast> fetchBagRelationships(List<Broadcast> broadcasts) {
        return Optional.of(broadcasts).map(this::fetchBroadcastPersonnelMembers).orElse(Collections.emptyList());
    }

    Broadcast fetchBroadcastPersonnelMembers(Broadcast result) {
        return entityManager
            .createQuery(
                "select broadcast from Broadcast broadcast left join fetch broadcast.broadcastPersonnelMembers where broadcast is :broadcast",
                Broadcast.class
            )
            .setParameter("broadcast", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Broadcast> fetchBroadcastPersonnelMembers(List<Broadcast> broadcasts) {
        return entityManager
            .createQuery(
                "select distinct broadcast from Broadcast broadcast left join fetch broadcast.broadcastPersonnelMembers where broadcast in :broadcasts",
                Broadcast.class
            )
            .setParameter("broadcasts", broadcasts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
