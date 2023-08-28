package grafismo.web.rest;

import grafismo.repository.StaffMemberRepository;
import grafismo.service.StaffMemberService;
import grafismo.service.dto.StaffMemberDTO;
import grafismo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link grafismo.domain.StaffMember}.
 */
@RestController
@RequestMapping("/api")
public class StaffMemberResource {

    private final Logger log = LoggerFactory.getLogger(StaffMemberResource.class);

    private static final String ENTITY_NAME = "staffMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaffMemberService staffMemberService;

    private final StaffMemberRepository staffMemberRepository;

    public StaffMemberResource(StaffMemberService staffMemberService, StaffMemberRepository staffMemberRepository) {
        this.staffMemberService = staffMemberService;
        this.staffMemberRepository = staffMemberRepository;
    }

    /**
     * {@code POST  /staff-members} : Create a new staffMember.
     *
     * @param staffMemberDTO the staffMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staffMemberDTO, or with status {@code 400 (Bad Request)} if the staffMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/staff-members")
    public ResponseEntity<StaffMemberDTO> createStaffMember(@Valid @RequestBody StaffMemberDTO staffMemberDTO) throws URISyntaxException {
        log.debug("REST request to save StaffMember : {}", staffMemberDTO);
        if (staffMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new staffMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaffMemberDTO result = staffMemberService.save(staffMemberDTO);
        return ResponseEntity
            .created(new URI("/api/staff-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /staff-members/:id} : Updates an existing staffMember.
     *
     * @param id the id of the staffMemberDTO to save.
     * @param staffMemberDTO the staffMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staffMemberDTO,
     * or with status {@code 400 (Bad Request)} if the staffMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staffMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/staff-members/{id}")
    public ResponseEntity<StaffMemberDTO> updateStaffMember(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StaffMemberDTO staffMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StaffMember : {}, {}", id, staffMemberDTO);
        if (staffMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staffMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StaffMemberDTO result = staffMemberService.update(staffMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staffMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /staff-members/:id} : Partial updates given fields of an existing staffMember, field will ignore if it is null
     *
     * @param id the id of the staffMemberDTO to save.
     * @param staffMemberDTO the staffMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staffMemberDTO,
     * or with status {@code 400 (Bad Request)} if the staffMemberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the staffMemberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the staffMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/staff-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StaffMemberDTO> partialUpdateStaffMember(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StaffMemberDTO staffMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StaffMember partially : {}, {}", id, staffMemberDTO);
        if (staffMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, staffMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!staffMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StaffMemberDTO> result = staffMemberService.partialUpdate(staffMemberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staffMemberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /staff-members} : get all the staffMembers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staffMembers in body.
     */
    @GetMapping("/staff-members")
    public ResponseEntity<List<StaffMemberDTO>> getAllStaffMembers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of StaffMembers");
        Page<StaffMemberDTO> page;
        if (eagerload) {
            page = staffMemberService.findAllWithEagerRelationships(pageable);
        } else {
            page = staffMemberService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /staff-members/:id} : get the "id" staffMember.
     *
     * @param id the id of the staffMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staffMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/staff-members/{id}")
    public ResponseEntity<StaffMemberDTO> getStaffMember(@PathVariable Long id) {
        log.debug("REST request to get StaffMember : {}", id);
        Optional<StaffMemberDTO> staffMemberDTO = staffMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffMemberDTO);
    }

    /**
     * {@code DELETE  /staff-members/:id} : delete the "id" staffMember.
     *
     * @param id the id of the staffMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/staff-members/{id}")
    public ResponseEntity<Void> deleteStaffMember(@PathVariable Long id) {
        log.debug("REST request to delete StaffMember : {}", id);
        staffMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
