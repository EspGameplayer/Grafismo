package grafismo.web.rest;

import grafismo.repository.BroadcastRepository;
import grafismo.service.BroadcastService;
import grafismo.service.dto.BroadcastDTO;
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
 * REST controller for managing {@link grafismo.domain.Broadcast}.
 */
@RestController
@RequestMapping("/api")
public class BroadcastResource {

    private final Logger log = LoggerFactory.getLogger(BroadcastResource.class);

    private static final String ENTITY_NAME = "broadcast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BroadcastService broadcastService;

    private final BroadcastRepository broadcastRepository;

    public BroadcastResource(BroadcastService broadcastService, BroadcastRepository broadcastRepository) {
        this.broadcastService = broadcastService;
        this.broadcastRepository = broadcastRepository;
    }

    /**
     * {@code POST  /broadcasts} : Create a new broadcast.
     *
     * @param broadcastDTO the broadcastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new broadcastDTO, or with status {@code 400 (Bad Request)} if the broadcast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/broadcasts")
    public ResponseEntity<BroadcastDTO> createBroadcast(@Valid @RequestBody BroadcastDTO broadcastDTO) throws URISyntaxException {
        log.debug("REST request to save Broadcast : {}", broadcastDTO);
        if (broadcastDTO.getId() != null) {
            throw new BadRequestAlertException("A new broadcast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BroadcastDTO result = broadcastService.save(broadcastDTO);
        return ResponseEntity
            .created(new URI("/api/broadcasts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /broadcasts/:id} : Updates an existing broadcast.
     *
     * @param id the id of the broadcastDTO to save.
     * @param broadcastDTO the broadcastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated broadcastDTO,
     * or with status {@code 400 (Bad Request)} if the broadcastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the broadcastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/broadcasts/{id}")
    public ResponseEntity<BroadcastDTO> updateBroadcast(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BroadcastDTO broadcastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Broadcast : {}, {}", id, broadcastDTO);
        if (broadcastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, broadcastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!broadcastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BroadcastDTO result = broadcastService.update(broadcastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, broadcastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /broadcasts/:id} : Partial updates given fields of an existing broadcast, field will ignore if it is null
     *
     * @param id the id of the broadcastDTO to save.
     * @param broadcastDTO the broadcastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated broadcastDTO,
     * or with status {@code 400 (Bad Request)} if the broadcastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the broadcastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the broadcastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/broadcasts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BroadcastDTO> partialUpdateBroadcast(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BroadcastDTO broadcastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Broadcast partially : {}, {}", id, broadcastDTO);
        if (broadcastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, broadcastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!broadcastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BroadcastDTO> result = broadcastService.partialUpdate(broadcastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, broadcastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /broadcasts} : get all the broadcasts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of broadcasts in body.
     */
    @GetMapping("/broadcasts")
    public ResponseEntity<List<BroadcastDTO>> getAllBroadcasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Broadcasts");
        Page<BroadcastDTO> page;
        if (eagerload) {
            page = broadcastService.findAllWithEagerRelationships(pageable);
        } else {
            page = broadcastService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /broadcasts/:id} : get the "id" broadcast.
     *
     * @param id the id of the broadcastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the broadcastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/broadcasts/{id}")
    public ResponseEntity<BroadcastDTO> getBroadcast(@PathVariable Long id) {
        log.debug("REST request to get Broadcast : {}", id);
        Optional<BroadcastDTO> broadcastDTO = broadcastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(broadcastDTO);
    }

    /**
     * {@code DELETE  /broadcasts/:id} : delete the "id" broadcast.
     *
     * @param id the id of the broadcastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/broadcasts/{id}")
    public ResponseEntity<Void> deleteBroadcast(@PathVariable Long id) {
        log.debug("REST request to delete Broadcast : {}", id);
        broadcastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
