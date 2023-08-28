package grafismo.repository;

import grafismo.domain.MatchAction;
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
public class MatchActionRepositoryWithBagRelationshipsImpl implements MatchActionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MatchAction> fetchBagRelationships(Optional<MatchAction> matchAction) {
        return matchAction.map(this::fetchMatchPlayers);
    }

    @Override
    public Page<MatchAction> fetchBagRelationships(Page<MatchAction> matchActions) {
        return new PageImpl<>(
            fetchBagRelationships(matchActions.getContent()),
            matchActions.getPageable(),
            matchActions.getTotalElements()
        );
    }

    @Override
    public List<MatchAction> fetchBagRelationships(List<MatchAction> matchActions) {
        return Optional.of(matchActions).map(this::fetchMatchPlayers).orElse(Collections.emptyList());
    }

    MatchAction fetchMatchPlayers(MatchAction result) {
        return entityManager
            .createQuery(
                "select matchAction from MatchAction matchAction left join fetch matchAction.matchPlayers where matchAction is :matchAction",
                MatchAction.class
            )
            .setParameter("matchAction", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<MatchAction> fetchMatchPlayers(List<MatchAction> matchActions) {
        return entityManager
            .createQuery(
                "select distinct matchAction from MatchAction matchAction left join fetch matchAction.matchPlayers where matchAction in :matchActions",
                MatchAction.class
            )
            .setParameter("matchActions", matchActions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
