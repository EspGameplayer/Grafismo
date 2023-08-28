package grafismo.service;

import grafismo.domain.Broadcast;
import grafismo.repository.BroadcastRepository;
import grafismo.service.dto.BroadcastDTO;
import grafismo.service.mapper.BroadcastMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Broadcast}.
 */
@Service
@Transactional
public class BroadcastService {

    private final Logger log = LoggerFactory.getLogger(BroadcastService.class);

    private final BroadcastRepository broadcastRepository;

    private final BroadcastMapper broadcastMapper;

    public BroadcastService(BroadcastRepository broadcastRepository, BroadcastMapper broadcastMapper) {
        this.broadcastRepository = broadcastRepository;
        this.broadcastMapper = broadcastMapper;
    }

    /**
     * Save a broadcast.
     *
     * @param broadcastDTO the entity to save.
     * @return the persisted entity.
     */
    public BroadcastDTO save(BroadcastDTO broadcastDTO) {
        log.debug("Request to save Broadcast : {}", broadcastDTO);
        Broadcast broadcast = broadcastMapper.toEntity(broadcastDTO);
        broadcast = broadcastRepository.save(broadcast);
        return broadcastMapper.toDto(broadcast);
    }

    /**
     * Update a broadcast.
     *
     * @param broadcastDTO the entity to save.
     * @return the persisted entity.
     */
    public BroadcastDTO update(BroadcastDTO broadcastDTO) {
        log.debug("Request to save Broadcast : {}", broadcastDTO);
        Broadcast broadcast = broadcastMapper.toEntity(broadcastDTO);
        broadcast = broadcastRepository.save(broadcast);
        return broadcastMapper.toDto(broadcast);
    }

    /**
     * Partially update a broadcast.
     *
     * @param broadcastDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BroadcastDTO> partialUpdate(BroadcastDTO broadcastDTO) {
        log.debug("Request to partially update Broadcast : {}", broadcastDTO);

        return broadcastRepository
            .findById(broadcastDTO.getId())
            .map(existingBroadcast -> {
                broadcastMapper.partialUpdate(existingBroadcast, broadcastDTO);

                return existingBroadcast;
            })
            .map(broadcastRepository::save)
            .map(broadcastMapper::toDto);
    }

    /**
     * Get all the broadcasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BroadcastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Broadcasts");
        return broadcastRepository.findAll(pageable).map(broadcastMapper::toDto);
    }

    /**
     * Get all the broadcasts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BroadcastDTO> findAllWithEagerRelationships(Pageable pageable) {
        return broadcastRepository.findAllWithEagerRelationships(pageable).map(broadcastMapper::toDto);
    }

    /**
     * Get one broadcast by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BroadcastDTO> findOne(Long id) {
        log.debug("Request to get Broadcast : {}", id);
        return broadcastRepository.findOneWithEagerRelationships(id).map(broadcastMapper::toDto);
    }

    /**
     * Delete the broadcast by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Broadcast : {}", id);
        broadcastRepository.deleteById(id);
    }
}
