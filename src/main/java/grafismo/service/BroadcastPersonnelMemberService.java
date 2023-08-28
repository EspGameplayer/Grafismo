package grafismo.service;

import grafismo.domain.BroadcastPersonnelMember;
import grafismo.repository.BroadcastPersonnelMemberRepository;
import grafismo.service.dto.BroadcastPersonnelMemberDTO;
import grafismo.service.mapper.BroadcastPersonnelMemberMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BroadcastPersonnelMember}.
 */
@Service
@Transactional
public class BroadcastPersonnelMemberService {

    private final Logger log = LoggerFactory.getLogger(BroadcastPersonnelMemberService.class);

    private final BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepository;

    private final BroadcastPersonnelMemberMapper broadcastPersonnelMemberMapper;

    public BroadcastPersonnelMemberService(
        BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepository,
        BroadcastPersonnelMemberMapper broadcastPersonnelMemberMapper
    ) {
        this.broadcastPersonnelMemberRepository = broadcastPersonnelMemberRepository;
        this.broadcastPersonnelMemberMapper = broadcastPersonnelMemberMapper;
    }

    /**
     * Save a broadcastPersonnelMember.
     *
     * @param broadcastPersonnelMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public BroadcastPersonnelMemberDTO save(BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO) {
        log.debug("Request to save BroadcastPersonnelMember : {}", broadcastPersonnelMemberDTO);
        BroadcastPersonnelMember broadcastPersonnelMember = broadcastPersonnelMemberMapper.toEntity(broadcastPersonnelMemberDTO);
        broadcastPersonnelMember = broadcastPersonnelMemberRepository.save(broadcastPersonnelMember);
        return broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);
    }

    /**
     * Update a broadcastPersonnelMember.
     *
     * @param broadcastPersonnelMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public BroadcastPersonnelMemberDTO update(BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO) {
        log.debug("Request to save BroadcastPersonnelMember : {}", broadcastPersonnelMemberDTO);
        BroadcastPersonnelMember broadcastPersonnelMember = broadcastPersonnelMemberMapper.toEntity(broadcastPersonnelMemberDTO);
        broadcastPersonnelMember = broadcastPersonnelMemberRepository.save(broadcastPersonnelMember);
        return broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);
    }

    /**
     * Partially update a broadcastPersonnelMember.
     *
     * @param broadcastPersonnelMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BroadcastPersonnelMemberDTO> partialUpdate(BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO) {
        log.debug("Request to partially update BroadcastPersonnelMember : {}", broadcastPersonnelMemberDTO);

        return broadcastPersonnelMemberRepository
            .findById(broadcastPersonnelMemberDTO.getId())
            .map(existingBroadcastPersonnelMember -> {
                broadcastPersonnelMemberMapper.partialUpdate(existingBroadcastPersonnelMember, broadcastPersonnelMemberDTO);

                return existingBroadcastPersonnelMember;
            })
            .map(broadcastPersonnelMemberRepository::save)
            .map(broadcastPersonnelMemberMapper::toDto);
    }

    /**
     * Get all the broadcastPersonnelMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BroadcastPersonnelMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BroadcastPersonnelMembers");
        return broadcastPersonnelMemberRepository.findAll(pageable).map(broadcastPersonnelMemberMapper::toDto);
    }

    /**
     * Get all the broadcastPersonnelMembers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BroadcastPersonnelMemberDTO> findAllWithEagerRelationships(Pageable pageable) {
        return broadcastPersonnelMemberRepository.findAllWithEagerRelationships(pageable).map(broadcastPersonnelMemberMapper::toDto);
    }

    /**
     * Get one broadcastPersonnelMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BroadcastPersonnelMemberDTO> findOne(Long id) {
        log.debug("Request to get BroadcastPersonnelMember : {}", id);
        return broadcastPersonnelMemberRepository.findOneWithEagerRelationships(id).map(broadcastPersonnelMemberMapper::toDto);
    }

    /**
     * Delete the broadcastPersonnelMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BroadcastPersonnelMember : {}", id);
        broadcastPersonnelMemberRepository.deleteById(id);
    }
}
