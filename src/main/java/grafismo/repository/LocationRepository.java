package grafismo.repository;

import grafismo.domain.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Location entity.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    default Optional<Location> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Location> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Location> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct location from Location location left join fetch location.country",
        countQuery = "select count(distinct location) from Location location"
    )
    Page<Location> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct location from Location location left join fetch location.country")
    List<Location> findAllWithToOneRelationships();

    @Query("select location from Location location left join fetch location.country where location.id =:id")
    Optional<Location> findOneWithToOneRelationships(@Param("id") Long id);
}
