package grafismo.web.rest;

import grafismo.repository.GraphicElementRepository;
import grafismo.service.GraphicElementService;
import grafismo.service.dto.GraphicElementDTO;
import grafismo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link grafismo.domain.GraphicElement}.
 */
@RestController
@RequestMapping("/api")
public class GraphicElementResource {

    private final Logger log = LoggerFactory.getLogger(GraphicElementResource.class);

    private static final String ENTITY_NAME = "graphicElement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GraphicElementService graphicElementService;

    private final GraphicElementRepository graphicElementRepository;

    public GraphicElementResource(GraphicElementService graphicElementService, GraphicElementRepository graphicElementRepository) {
        this.graphicElementService = graphicElementService;
        this.graphicElementRepository = graphicElementRepository;
    }

    /**
     * {@code POST  /graphic-elements} : Create a new graphicElement.
     *
     * @param graphicElementDTO the graphicElementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new graphicElementDTO, or with status {@code 400 (Bad Request)} if the graphicElement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/graphic-elements")
    public ResponseEntity<GraphicElementDTO> createGraphicElement(@Valid @RequestBody GraphicElementDTO graphicElementDTO)
        throws URISyntaxException {
        log.debug("REST request to save GraphicElement : {}", graphicElementDTO);
        if (graphicElementDTO.getId() != null) {
            throw new BadRequestAlertException("A new graphicElement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GraphicElementDTO result = graphicElementService.save(graphicElementDTO);
        return ResponseEntity
            .created(new URI("/api/graphic-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /graphic-elements/:id} : Updates an existing graphicElement.
     *
     * @param id the id of the graphicElementDTO to save.
     * @param graphicElementDTO the graphicElementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated graphicElementDTO,
     * or with status {@code 400 (Bad Request)} if the graphicElementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the graphicElementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/graphic-elements/{id}")
    public ResponseEntity<GraphicElementDTO> updateGraphicElement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GraphicElementDTO graphicElementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GraphicElement : {}, {}", id, graphicElementDTO);
        if (graphicElementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, graphicElementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!graphicElementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GraphicElementDTO result = graphicElementService.update(graphicElementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, graphicElementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /graphic-elements/:id} : Partial updates given fields of an existing graphicElement, field will ignore if it is null
     *
     * @param id the id of the graphicElementDTO to save.
     * @param graphicElementDTO the graphicElementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated graphicElementDTO,
     * or with status {@code 400 (Bad Request)} if the graphicElementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the graphicElementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the graphicElementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/graphic-elements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GraphicElementDTO> partialUpdateGraphicElement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GraphicElementDTO graphicElementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GraphicElement partially : {}, {}", id, graphicElementDTO);
        if (graphicElementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, graphicElementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!graphicElementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GraphicElementDTO> result = graphicElementService.partialUpdate(graphicElementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, graphicElementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /graphic-elements} : get all the graphicElements.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of graphicElements in body.
     */
    @GetMapping("/graphic-elements")
    public ResponseEntity<List<GraphicElementDTO>> getAllGraphicElements(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("keys-is-null".equals(filter)) {
            log.debug("REST request to get all GraphicElements where keys is null");
            return new ResponseEntity<>(graphicElementService.findAllWhereKeysIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of GraphicElements");
        Page<GraphicElementDTO> page = graphicElementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /graphic-elements/:id} : get the "id" graphicElement.
     *
     * @param id the id of the graphicElementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the graphicElementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/graphic-elements/{id}")
    public ResponseEntity<GraphicElementDTO> getGraphicElement(@PathVariable Long id) {
        log.debug("REST request to get GraphicElement : {}", id);
        Optional<GraphicElementDTO> graphicElementDTO = graphicElementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(graphicElementDTO);
    }

    /**
     * {@code DELETE  /graphic-elements/:id} : delete the "id" graphicElement.
     *
     * @param id the id of the graphicElementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/graphic-elements/{id}")
    public ResponseEntity<Void> deleteGraphicElement(@PathVariable Long id) {
        log.debug("REST request to delete GraphicElement : {}", id);
        graphicElementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
