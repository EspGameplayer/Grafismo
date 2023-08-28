package grafismo.repository;

import grafismo.domain.GraphicElement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GraphicElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GraphicElementRepository extends JpaRepository<GraphicElement, Long> {}
