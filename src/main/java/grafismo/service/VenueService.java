package grafismo.service;

import grafismo.domain.Venue;
import grafismo.repository.VenueRepository;
import grafismo.service.dto.VenueDTO;
import grafismo.service.mapper.VenueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venue}.
 */
@Service
@Transactional
public class VenueService {

    private final Logger log = LoggerFactory.getLogger(VenueService.class);

    private final VenueRepository venueRepository;

    private final VenueMapper venueMapper;

    public VenueService(VenueRepository venueRepository, VenueMapper venueMapper) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
    }

    /**
     * Save a venue.
     *
     * @param venueDTO the entity to save.
     * @return the persisted entity.
     */
    public VenueDTO save(VenueDTO venueDTO) {
        log.debug("Request to save Venue : {}", venueDTO);
        Venue venue = venueMapper.toEntity(venueDTO);
        venue = venueRepository.save(venue);
        return venueMapper.toDto(venue);
    }

    /**
     * Update a venue.
     *
     * @param venueDTO the entity to save.
     * @return the persisted entity.
     */
    public VenueDTO update(VenueDTO venueDTO) {
        log.debug("Request to save Venue : {}", venueDTO);
        Venue venue = venueMapper.toEntity(venueDTO);
        venue = venueRepository.save(venue);
        return venueMapper.toDto(venue);
    }

    /**
     * Partially update a venue.
     *
     * @param venueDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VenueDTO> partialUpdate(VenueDTO venueDTO) {
        log.debug("Request to partially update Venue : {}", venueDTO);

        return venueRepository
            .findById(venueDTO.getId())
            .map(existingVenue -> {
                venueMapper.partialUpdate(existingVenue, venueDTO);

                return existingVenue;
            })
            .map(venueRepository::save)
            .map(venueMapper::toDto);
    }

    /**
     * Get all the venues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VenueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Venues");
        return venueRepository.findAll(pageable).map(venueMapper::toDto);
    }

    /**
     * Get all the venues with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VenueDTO> findAllWithEagerRelationships(Pageable pageable) {
        return venueRepository.findAllWithEagerRelationships(pageable).map(venueMapper::toDto);
    }

    /**
     * Get one venue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VenueDTO> findOne(Long id) {
        log.debug("Request to get Venue : {}", id);
        return venueRepository.findOneWithEagerRelationships(id).map(venueMapper::toDto);
    }

    /**
     * Delete the venue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Venue : {}", id);
        venueRepository.deleteById(id);
    }
}
