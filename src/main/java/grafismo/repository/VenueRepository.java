package grafismo.repository;

import grafismo.domain.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Venue entity.
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    default Optional<Venue> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Venue> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Venue> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct venue from Venue venue left join fetch venue.location",
        countQuery = "select count(distinct venue) from Venue venue"
    )
    Page<Venue> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct venue from Venue venue left join fetch venue.location")
    List<Venue> findAllWithToOneRelationships();

    @Query("select venue from Venue venue left join fetch venue.location where venue.id =:id")
    Optional<Venue> findOneWithToOneRelationships(@Param("id") Long id);
}
