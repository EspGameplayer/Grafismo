package grafismo.repository;

import grafismo.domain.TemplateFormation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TemplateFormation entity.
 */
@Repository
public interface TemplateFormationRepository extends JpaRepository<TemplateFormation, Long> {
    default Optional<TemplateFormation> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TemplateFormation> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TemplateFormation> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct templateFormation from TemplateFormation templateFormation left join fetch templateFormation.formation",
        countQuery = "select count(distinct templateFormation) from TemplateFormation templateFormation"
    )
    Page<TemplateFormation> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct templateFormation from TemplateFormation templateFormation left join fetch templateFormation.formation")
    List<TemplateFormation> findAllWithToOneRelationships();

    @Query(
        "select templateFormation from TemplateFormation templateFormation left join fetch templateFormation.formation where templateFormation.id =:id"
    )
    Optional<TemplateFormation> findOneWithToOneRelationships(@Param("id") Long id);
}
