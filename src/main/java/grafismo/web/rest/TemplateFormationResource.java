package grafismo.web.rest;

import grafismo.repository.TemplateFormationRepository;
import grafismo.service.TemplateFormationService;
import grafismo.service.dto.TemplateFormationDTO;
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
 * REST controller for managing {@link grafismo.domain.TemplateFormation}.
 */
@RestController
@RequestMapping("/api")
public class TemplateFormationResource {

    private final Logger log = LoggerFactory.getLogger(TemplateFormationResource.class);

    private static final String ENTITY_NAME = "templateFormation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateFormationService templateFormationService;

    private final TemplateFormationRepository templateFormationRepository;

    public TemplateFormationResource(
        TemplateFormationService templateFormationService,
        TemplateFormationRepository templateFormationRepository
    ) {
        this.templateFormationService = templateFormationService;
        this.templateFormationRepository = templateFormationRepository;
    }

    /**
     * {@code POST  /template-formations} : Create a new templateFormation.
     *
     * @param templateFormationDTO the templateFormationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateFormationDTO, or with status {@code 400 (Bad Request)} if the templateFormation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-formations")
    public ResponseEntity<TemplateFormationDTO> createTemplateFormation(@Valid @RequestBody TemplateFormationDTO templateFormationDTO)
        throws URISyntaxException {
        log.debug("REST request to save TemplateFormation : {}", templateFormationDTO);
        if (templateFormationDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateFormation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateFormationDTO result = templateFormationService.save(templateFormationDTO);
        return ResponseEntity
            .created(new URI("/api/template-formations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-formations/:id} : Updates an existing templateFormation.
     *
     * @param id the id of the templateFormationDTO to save.
     * @param templateFormationDTO the templateFormationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormationDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateFormationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-formations/{id}")
    public ResponseEntity<TemplateFormationDTO> updateTemplateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TemplateFormationDTO templateFormationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateFormation : {}, {}", id, templateFormationDTO);
        if (templateFormationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateFormationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateFormationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateFormationDTO result = templateFormationService.update(templateFormationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateFormationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-formations/:id} : Partial updates given fields of an existing templateFormation, field will ignore if it is null
     *
     * @param id the id of the templateFormationDTO to save.
     * @param templateFormationDTO the templateFormationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateFormationDTO,
     * or with status {@code 400 (Bad Request)} if the templateFormationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateFormationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateFormationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-formations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateFormationDTO> partialUpdateTemplateFormation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TemplateFormationDTO templateFormationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateFormation partially : {}, {}", id, templateFormationDTO);
        if (templateFormationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateFormationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateFormationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateFormationDTO> result = templateFormationService.partialUpdate(templateFormationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateFormationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-formations} : get all the templateFormations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateFormations in body.
     */
    @GetMapping("/template-formations")
    public ResponseEntity<List<TemplateFormationDTO>> getAllTemplateFormations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TemplateFormations");
        Page<TemplateFormationDTO> page;
        if (eagerload) {
            page = templateFormationService.findAllWithEagerRelationships(pageable);
        } else {
            page = templateFormationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /template-formations/:id} : get the "id" templateFormation.
     *
     * @param id the id of the templateFormationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateFormationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-formations/{id}")
    public ResponseEntity<TemplateFormationDTO> getTemplateFormation(@PathVariable Long id) {
        log.debug("REST request to get TemplateFormation : {}", id);
        Optional<TemplateFormationDTO> templateFormationDTO = templateFormationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateFormationDTO);
    }

    /**
     * {@code DELETE  /template-formations/:id} : delete the "id" templateFormation.
     *
     * @param id the id of the templateFormationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-formations/{id}")
    public ResponseEntity<Void> deleteTemplateFormation(@PathVariable Long id) {
        log.debug("REST request to delete TemplateFormation : {}", id);
        templateFormationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
