package grafismo.repository;

import grafismo.domain.StaffMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StaffMember entity.
 */
@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {
    default Optional<StaffMember> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StaffMember> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StaffMember> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct staffMember from StaffMember staffMember left join fetch staffMember.person",
        countQuery = "select count(distinct staffMember) from StaffMember staffMember"
    )
    Page<StaffMember> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct staffMember from StaffMember staffMember left join fetch staffMember.person")
    List<StaffMember> findAllWithToOneRelationships();

    @Query("select staffMember from StaffMember staffMember left join fetch staffMember.person where staffMember.id =:id")
    Optional<StaffMember> findOneWithToOneRelationships(@Param("id") Long id);
}
