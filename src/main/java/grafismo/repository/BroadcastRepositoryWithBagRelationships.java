package grafismo.repository;

import grafismo.domain.Broadcast;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface BroadcastRepositoryWithBagRelationships {
    Optional<Broadcast> fetchBagRelationships(Optional<Broadcast> broadcast);

    List<Broadcast> fetchBagRelationships(List<Broadcast> broadcasts);

    Page<Broadcast> fetchBagRelationships(Page<Broadcast> broadcasts);
}
