package grafismo.web.rest;

import grafismo.repository.BroadcastPersonnelMemberRepository;
import grafismo.service.BroadcastPersonnelMemberService;
import grafismo.service.dto.BroadcastPersonnelMemberDTO;
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
 * REST controller for managing {@link grafismo.domain.BroadcastPersonnelMember}.
 */
@RestController
@RequestMapping("/api")
public class BroadcastPersonnelMemberResource {

    private final Logger log = LoggerFactory.getLogger(BroadcastPersonnelMemberResource.class);

    private static final String ENTITY_NAME = "broadcastPersonnelMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BroadcastPersonnelMemberService broadcastPersonnelMemberService;

    private final BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepository;

    public BroadcastPersonnelMemberResource(
        BroadcastPersonnelMemberService broadcastPersonnelMemberService,
        BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepository
    ) {
        this.broadcastPersonnelMemberService = broadcastPersonnelMemberService;
        this.broadcastPersonnelMemberRepository = broadcastPersonnelMemberRepository;
    }

    /**
     * {@code POST  /broadcast-personnel-members} : Create a new broadcastPersonnelMember.
     *
     * @param broadcastPersonnelMemberDTO the broadcastPersonnelMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new broadcastPersonnelMemberDTO, or with status {@code 400 (Bad Request)} if the broadcastPersonnelMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/broadcast-personnel-members")
    public ResponseEntity<BroadcastPersonnelMemberDTO> createBroadcastPersonnelMember(
        @Valid @RequestBody BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to save BroadcastPersonnelMember : {}", broadcastPersonnelMemberDTO);
        if (broadcastPersonnelMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new broadcastPersonnelMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BroadcastPersonnelMemberDTO result = broadcastPersonnelMemberService.save(broadcastPersonnelMemberDTO);
        return ResponseEntity
            .created(new URI("/api/broadcast-personnel-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /broadcast-personnel-members/:id} : Updates an existing broadcastPersonnelMember.
     *
     * @param id the id of the broadcastPersonnelMemberDTO to save.
     * @param broadcastPersonnelMemberDTO the broadcastPersonnelMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated broadcastPersonnelMemberDTO,
     * or with status {@code 400 (Bad Request)} if the broadcastPersonnelMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the broadcastPersonnelMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/broadcast-personnel-members/{id}")
    public ResponseEntity<BroadcastPersonnelMemberDTO> updateBroadcastPersonnelMember(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BroadcastPersonnelMember : {}, {}", id, broadcastPersonnelMemberDTO);
        if (broadcastPersonnelMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, broadcastPersonnelMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!broadcastPersonnelMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BroadcastPersonnelMemberDTO result = broadcastPersonnelMemberService.update(broadcastPersonnelMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, broadcastPersonnelMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /broadcast-personnel-members/:id} : Partial updates given fields of an existing broadcastPersonnelMember, field will ignore if it is null
     *
     * @param id the id of the broadcastPersonnelMemberDTO to save.
     * @param broadcastPersonnelMemberDTO the broadcastPersonnelMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated broadcastPersonnelMemberDTO,
     * or with status {@code 400 (Bad Request)} if the broadcastPersonnelMemberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the broadcastPersonnelMemberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the broadcastPersonnelMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/broadcast-personnel-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BroadcastPersonnelMemberDTO> partialUpdateBroadcastPersonnelMember(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BroadcastPersonnelMember partially : {}, {}", id, broadcastPersonnelMemberDTO);
        if (broadcastPersonnelMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, broadcastPersonnelMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!broadcastPersonnelMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BroadcastPersonnelMemberDTO> result = broadcastPersonnelMemberService.partialUpdate(broadcastPersonnelMemberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, broadcastPersonnelMemberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /broadcast-personnel-members} : get all the broadcastPersonnelMembers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of broadcastPersonnelMembers in body.
     */
    @GetMapping("/broadcast-personnel-members")
    public ResponseEntity<List<BroadcastPersonnelMemberDTO>> getAllBroadcastPersonnelMembers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of BroadcastPersonnelMembers");
        Page<BroadcastPersonnelMemberDTO> page;
        if (eagerload) {
            page = broadcastPersonnelMemberService.findAllWithEagerRelationships(pageable);
        } else {
            page = broadcastPersonnelMemberService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /broadcast-personnel-members/:id} : get the "id" broadcastPersonnelMember.
     *
     * @param id the id of the broadcastPersonnelMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the broadcastPersonnelMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/broadcast-personnel-members/{id}")
    public ResponseEntity<BroadcastPersonnelMemberDTO> getBroadcastPersonnelMember(@PathVariable Long id) {
        log.debug("REST request to get BroadcastPersonnelMember : {}", id);
        Optional<BroadcastPersonnelMemberDTO> broadcastPersonnelMemberDTO = broadcastPersonnelMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(broadcastPersonnelMemberDTO);
    }

    /**
     * {@code DELETE  /broadcast-personnel-members/:id} : delete the "id" broadcastPersonnelMember.
     *
     * @param id the id of the broadcastPersonnelMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/broadcast-personnel-members/{id}")
    public ResponseEntity<Void> deleteBroadcastPersonnelMember(@PathVariable Long id) {
        log.debug("REST request to delete BroadcastPersonnelMember : {}", id);
        broadcastPersonnelMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
