package grafismo.repository;

import grafismo.domain.BroadcastPersonnelMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BroadcastPersonnelMember entity.
 */
@Repository
public interface BroadcastPersonnelMemberRepository extends JpaRepository<BroadcastPersonnelMember, Long> {
    default Optional<BroadcastPersonnelMember> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BroadcastPersonnelMember> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BroadcastPersonnelMember> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct broadcastPersonnelMember from BroadcastPersonnelMember broadcastPersonnelMember left join fetch broadcastPersonnelMember.person",
        countQuery = "select count(distinct broadcastPersonnelMember) from BroadcastPersonnelMember broadcastPersonnelMember"
    )
    Page<BroadcastPersonnelMember> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct broadcastPersonnelMember from BroadcastPersonnelMember broadcastPersonnelMember left join fetch broadcastPersonnelMember.person"
    )
    List<BroadcastPersonnelMember> findAllWithToOneRelationships();

    @Query(
        "select broadcastPersonnelMember from BroadcastPersonnelMember broadcastPersonnelMember left join fetch broadcastPersonnelMember.person where broadcastPersonnelMember.id =:id"
    )
    Optional<BroadcastPersonnelMember> findOneWithToOneRelationships(@Param("id") Long id);
}
