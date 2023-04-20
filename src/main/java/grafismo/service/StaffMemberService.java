package grafismo.service;

import grafismo.domain.StaffMember;
import grafismo.repository.StaffMemberRepository;
import grafismo.service.dto.StaffMemberDTO;
import grafismo.service.mapper.StaffMemberMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StaffMember}.
 */
@Service
@Transactional
public class StaffMemberService {

    private final Logger log = LoggerFactory.getLogger(StaffMemberService.class);

    private final StaffMemberRepository staffMemberRepository;

    private final StaffMemberMapper staffMemberMapper;

    public StaffMemberService(StaffMemberRepository staffMemberRepository, StaffMemberMapper staffMemberMapper) {
        this.staffMemberRepository = staffMemberRepository;
        this.staffMemberMapper = staffMemberMapper;
    }

    /**
     * Save a staffMember.
     *
     * @param staffMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public StaffMemberDTO save(StaffMemberDTO staffMemberDTO) {
        log.debug("Request to save StaffMember : {}", staffMemberDTO);
        StaffMember staffMember = staffMemberMapper.toEntity(staffMemberDTO);
        staffMember = staffMemberRepository.save(staffMember);
        return staffMemberMapper.toDto(staffMember);
    }

    /**
     * Update a staffMember.
     *
     * @param staffMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public StaffMemberDTO update(StaffMemberDTO staffMemberDTO) {
        log.debug("Request to save StaffMember : {}", staffMemberDTO);
        StaffMember staffMember = staffMemberMapper.toEntity(staffMemberDTO);
        staffMember = staffMemberRepository.save(staffMember);
        return staffMemberMapper.toDto(staffMember);
    }

    /**
     * Partially update a staffMember.
     *
     * @param staffMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StaffMemberDTO> partialUpdate(StaffMemberDTO staffMemberDTO) {
        log.debug("Request to partially update StaffMember : {}", staffMemberDTO);

        return staffMemberRepository
            .findById(staffMemberDTO.getId())
            .map(existingStaffMember -> {
                staffMemberMapper.partialUpdate(existingStaffMember, staffMemberDTO);

                return existingStaffMember;
            })
            .map(staffMemberRepository::save)
            .map(staffMemberMapper::toDto);
    }

    /**
     * Get all the staffMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StaffMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StaffMembers");
        return staffMemberRepository.findAll(pageable).map(staffMemberMapper::toDto);
    }

    /**
     * Get all the staffMembers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StaffMemberDTO> findAllWithEagerRelationships(Pageable pageable) {
        return staffMemberRepository.findAllWithEagerRelationships(pageable).map(staffMemberMapper::toDto);
    }

    /**
     * Get one staffMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StaffMemberDTO> findOne(Long id) {
        log.debug("Request to get StaffMember : {}", id);
        return staffMemberRepository.findOneWithEagerRelationships(id).map(staffMemberMapper::toDto);
    }

    /**
     * Delete the staffMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StaffMember : {}", id);
        staffMemberRepository.deleteById(id);
    }
}
