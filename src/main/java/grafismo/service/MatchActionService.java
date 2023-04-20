package grafismo.service;

import grafismo.domain.MatchAction;
import grafismo.repository.MatchActionRepository;
import grafismo.service.dto.MatchActionDTO;
import grafismo.service.mapper.MatchActionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MatchAction}.
 */
@Service
@Transactional
public class MatchActionService {

    private final Logger log = LoggerFactory.getLogger(MatchActionService.class);

    private final MatchActionRepository matchActionRepository;

    private final MatchActionMapper matchActionMapper;

    public MatchActionService(MatchActionRepository matchActionRepository, MatchActionMapper matchActionMapper) {
        this.matchActionRepository = matchActionRepository;
        this.matchActionMapper = matchActionMapper;
    }

    /**
     * Save a matchAction.
     *
     * @param matchActionDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchActionDTO save(MatchActionDTO matchActionDTO) {
        log.debug("Request to save MatchAction : {}", matchActionDTO);
        MatchAction matchAction = matchActionMapper.toEntity(matchActionDTO);
        matchAction = matchActionRepository.save(matchAction);
        return matchActionMapper.toDto(matchAction);
    }

    /**
     * Update a matchAction.
     *
     * @param matchActionDTO the entity to save.
     * @return the persisted entity.
     */
    public MatchActionDTO update(MatchActionDTO matchActionDTO) {
        log.debug("Request to save MatchAction : {}", matchActionDTO);
        MatchAction matchAction = matchActionMapper.toEntity(matchActionDTO);
        matchAction = matchActionRepository.save(matchAction);
        return matchActionMapper.toDto(matchAction);
    }

    /**
     * Partially update a matchAction.
     *
     * @param matchActionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MatchActionDTO> partialUpdate(MatchActionDTO matchActionDTO) {
        log.debug("Request to partially update MatchAction : {}", matchActionDTO);

        return matchActionRepository
            .findById(matchActionDTO.getId())
            .map(existingMatchAction -> {
                matchActionMapper.partialUpdate(existingMatchAction, matchActionDTO);

                return existingMatchAction;
            })
            .map(matchActionRepository::save)
            .map(matchActionMapper::toDto);
    }

    /**
     * Get all the matchActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MatchActionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MatchActions");
        return matchActionRepository.findAll(pageable).map(matchActionMapper::toDto);
    }

    /**
     * Get all the matchActions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MatchActionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return matchActionRepository.findAllWithEagerRelationships(pageable).map(matchActionMapper::toDto);
    }

    /**
     * Get one matchAction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MatchActionDTO> findOne(Long id) {
        log.debug("Request to get MatchAction : {}", id);
        return matchActionRepository.findOneWithEagerRelationships(id).map(matchActionMapper::toDto);
    }

    /**
     * Delete the matchAction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MatchAction : {}", id);
        matchActionRepository.deleteById(id);
    }
}
