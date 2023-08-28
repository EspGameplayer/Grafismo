package grafismo.repository;

import grafismo.domain.Injury;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Injury entity.
 */
@Repository
public interface InjuryRepository extends JpaRepository<Injury, Long> {
    default Optional<Injury> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Injury> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Injury> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct injury from Injury injury left join fetch injury.player",
        countQuery = "select count(distinct injury) from Injury injury"
    )
    Page<Injury> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct injury from Injury injury left join fetch injury.player")
    List<Injury> findAllWithToOneRelationships();

    @Query("select injury from Injury injury left join fetch injury.player where injury.id =:id")
    Optional<Injury> findOneWithToOneRelationships(@Param("id") Long id);
}
