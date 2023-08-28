package grafismo.repository;

import grafismo.domain.TeamPlayer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TeamPlayer entity.
 */
@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
    default Optional<TeamPlayer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TeamPlayer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TeamPlayer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct teamPlayer from TeamPlayer teamPlayer left join fetch teamPlayer.team left join fetch teamPlayer.player",
        countQuery = "select count(distinct teamPlayer) from TeamPlayer teamPlayer"
    )
    Page<TeamPlayer> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct teamPlayer from TeamPlayer teamPlayer left join fetch teamPlayer.team left join fetch teamPlayer.player")
    List<TeamPlayer> findAllWithToOneRelationships();

    @Query(
        "select teamPlayer from TeamPlayer teamPlayer left join fetch teamPlayer.team left join fetch teamPlayer.player where teamPlayer.id =:id"
    )
    Optional<TeamPlayer> findOneWithToOneRelationships(@Param("id") Long id);
}
