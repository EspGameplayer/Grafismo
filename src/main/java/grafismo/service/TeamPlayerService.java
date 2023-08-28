package grafismo.service;

import grafismo.domain.TeamPlayer;
import grafismo.repository.TeamPlayerRepository;
import grafismo.service.dto.TeamPlayerDTO;
import grafismo.service.mapper.TeamPlayerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TeamPlayer}.
 */
@Service
@Transactional
public class TeamPlayerService {

    private final Logger log = LoggerFactory.getLogger(TeamPlayerService.class);

    private final TeamPlayerRepository teamPlayerRepository;

    private final TeamPlayerMapper teamPlayerMapper;

    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository, TeamPlayerMapper teamPlayerMapper) {
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamPlayerMapper = teamPlayerMapper;
    }

    /**
     * Save a teamPlayer.
     *
     * @param teamPlayerDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamPlayerDTO save(TeamPlayerDTO teamPlayerDTO) {
        log.debug("Request to save TeamPlayer : {}", teamPlayerDTO);
        TeamPlayer teamPlayer = teamPlayerMapper.toEntity(teamPlayerDTO);
        teamPlayer = teamPlayerRepository.save(teamPlayer);
        return teamPlayerMapper.toDto(teamPlayer);
    }

    /**
     * Update a teamPlayer.
     *
     * @param teamPlayerDTO the entity to save.
     * @return the persisted entity.
     */
    public TeamPlayerDTO update(TeamPlayerDTO teamPlayerDTO) {
        log.debug("Request to save TeamPlayer : {}", teamPlayerDTO);
        TeamPlayer teamPlayer = teamPlayerMapper.toEntity(teamPlayerDTO);
        teamPlayer = teamPlayerRepository.save(teamPlayer);
        return teamPlayerMapper.toDto(teamPlayer);
    }

    /**
     * Partially update a teamPlayer.
     *
     * @param teamPlayerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TeamPlayerDTO> partialUpdate(TeamPlayerDTO teamPlayerDTO) {
        log.debug("Request to partially update TeamPlayer : {}", teamPlayerDTO);

        return teamPlayerRepository
            .findById(teamPlayerDTO.getId())
            .map(existingTeamPlayer -> {
                teamPlayerMapper.partialUpdate(existingTeamPlayer, teamPlayerDTO);

                return existingTeamPlayer;
            })
            .map(teamPlayerRepository::save)
            .map(teamPlayerMapper::toDto);
    }

    /**
     * Get all the teamPlayers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamPlayerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeamPlayers");
        return teamPlayerRepository.findAll(pageable).map(teamPlayerMapper::toDto);
    }

    /**
     * Get all the teamPlayers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TeamPlayerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teamPlayerRepository.findAllWithEagerRelationships(pageable).map(teamPlayerMapper::toDto);
    }

    /**
     * Get one teamPlayer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TeamPlayerDTO> findOne(Long id) {
        log.debug("Request to get TeamPlayer : {}", id);
        return teamPlayerRepository.findOneWithEagerRelationships(id).map(teamPlayerMapper::toDto);
    }

    /**
     * Delete the teamPlayer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TeamPlayer : {}", id);
        teamPlayerRepository.deleteById(id);
    }
}
