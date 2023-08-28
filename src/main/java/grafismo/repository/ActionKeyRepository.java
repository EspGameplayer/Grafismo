package grafismo.repository;

import grafismo.domain.ActionKey;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ActionKey entity.
 */
@Repository
public interface ActionKeyRepository extends JpaRepository<ActionKey, Long> {
    default Optional<ActionKey> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ActionKey> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ActionKey> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct actionKey from ActionKey actionKey left join fetch actionKey.graphicElement",
        countQuery = "select count(distinct actionKey) from ActionKey actionKey"
    )
    Page<ActionKey> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct actionKey from ActionKey actionKey left join fetch actionKey.graphicElement")
    List<ActionKey> findAllWithToOneRelationships();

    @Query("select actionKey from ActionKey actionKey left join fetch actionKey.graphicElement where actionKey.id =:id")
    Optional<ActionKey> findOneWithToOneRelationships(@Param("id") Long id);
}
