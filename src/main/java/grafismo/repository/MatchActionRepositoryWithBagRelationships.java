package grafismo.repository;

import grafismo.domain.MatchAction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MatchActionRepositoryWithBagRelationships {
    Optional<MatchAction> fetchBagRelationships(Optional<MatchAction> matchAction);

    List<MatchAction> fetchBagRelationships(List<MatchAction> matchActions);

    Page<MatchAction> fetchBagRelationships(Page<MatchAction> matchActions);
}
