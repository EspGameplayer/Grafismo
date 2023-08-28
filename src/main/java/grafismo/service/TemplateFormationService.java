package grafismo.service;

import grafismo.domain.TemplateFormation;
import grafismo.repository.TemplateFormationRepository;
import grafismo.service.dto.TemplateFormationDTO;
import grafismo.service.mapper.TemplateFormationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemplateFormation}.
 */
@Service
@Transactional
public class TemplateFormationService {

    private final Logger log = LoggerFactory.getLogger(TemplateFormationService.class);

    private final TemplateFormationRepository templateFormationRepository;

    private final TemplateFormationMapper templateFormationMapper;

    public TemplateFormationService(
        TemplateFormationRepository templateFormationRepository,
        TemplateFormationMapper templateFormationMapper
    ) {
        this.templateFormationRepository = templateFormationRepository;
        this.templateFormationMapper = templateFormationMapper;
    }

    /**
     * Save a templateFormation.
     *
     * @param templateFormationDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateFormationDTO save(TemplateFormationDTO templateFormationDTO) {
        log.debug("Request to save TemplateFormation : {}", templateFormationDTO);
        TemplateFormation templateFormation = templateFormationMapper.toEntity(templateFormationDTO);
        templateFormation = templateFormationRepository.save(templateFormation);
        return templateFormationMapper.toDto(templateFormation);
    }

    /**
     * Update a templateFormation.
     *
     * @param templateFormationDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateFormationDTO update(TemplateFormationDTO templateFormationDTO) {
        log.debug("Request to save TemplateFormation : {}", templateFormationDTO);
        TemplateFormation templateFormation = templateFormationMapper.toEntity(templateFormationDTO);
        templateFormation = templateFormationRepository.save(templateFormation);
        return templateFormationMapper.toDto(templateFormation);
    }

    /**
     * Partially update a templateFormation.
     *
     * @param templateFormationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TemplateFormationDTO> partialUpdate(TemplateFormationDTO templateFormationDTO) {
        log.debug("Request to partially update TemplateFormation : {}", templateFormationDTO);

        return templateFormationRepository
            .findById(templateFormationDTO.getId())
            .map(existingTemplateFormation -> {
                templateFormationMapper.partialUpdate(existingTemplateFormation, templateFormationDTO);

                return existingTemplateFormation;
            })
            .map(templateFormationRepository::save)
            .map(templateFormationMapper::toDto);
    }

    /**
     * Get all the templateFormations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateFormationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TemplateFormations");
        return templateFormationRepository.findAll(pageable).map(templateFormationMapper::toDto);
    }

    /**
     * Get all the templateFormations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TemplateFormationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return templateFormationRepository.findAllWithEagerRelationships(pageable).map(templateFormationMapper::toDto);
    }

    /**
     * Get one templateFormation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateFormationDTO> findOne(Long id) {
        log.debug("Request to get TemplateFormation : {}", id);
        return templateFormationRepository.findOneWithEagerRelationships(id).map(templateFormationMapper::toDto);
    }

    /**
     * Delete the templateFormation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemplateFormation : {}", id);
        templateFormationRepository.deleteById(id);
    }
}
