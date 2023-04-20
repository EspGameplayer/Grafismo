package grafismo.service;

import grafismo.domain.GraphicElement;
import grafismo.repository.GraphicElementRepository;
import grafismo.service.dto.GraphicElementDTO;
import grafismo.service.mapper.GraphicElementMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GraphicElement}.
 */
@Service
@Transactional
public class GraphicElementService {

    private final Logger log = LoggerFactory.getLogger(GraphicElementService.class);

    private final GraphicElementRepository graphicElementRepository;

    private final GraphicElementMapper graphicElementMapper;

    public GraphicElementService(GraphicElementRepository graphicElementRepository, GraphicElementMapper graphicElementMapper) {
        this.graphicElementRepository = graphicElementRepository;
        this.graphicElementMapper = graphicElementMapper;
    }

    /**
     * Save a graphicElement.
     *
     * @param graphicElementDTO the entity to save.
     * @return the persisted entity.
     */
    public GraphicElementDTO save(GraphicElementDTO graphicElementDTO) {
        log.debug("Request to save GraphicElement : {}", graphicElementDTO);
        GraphicElement graphicElement = graphicElementMapper.toEntity(graphicElementDTO);
        graphicElement = graphicElementRepository.save(graphicElement);
        return graphicElementMapper.toDto(graphicElement);
    }

    /**
     * Update a graphicElement.
     *
     * @param graphicElementDTO the entity to save.
     * @return the persisted entity.
     */
    public GraphicElementDTO update(GraphicElementDTO graphicElementDTO) {
        log.debug("Request to save GraphicElement : {}", graphicElementDTO);
        GraphicElement graphicElement = graphicElementMapper.toEntity(graphicElementDTO);
        graphicElement = graphicElementRepository.save(graphicElement);
        return graphicElementMapper.toDto(graphicElement);
    }

    /**
     * Partially update a graphicElement.
     *
     * @param graphicElementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GraphicElementDTO> partialUpdate(GraphicElementDTO graphicElementDTO) {
        log.debug("Request to partially update GraphicElement : {}", graphicElementDTO);

        return graphicElementRepository
            .findById(graphicElementDTO.getId())
            .map(existingGraphicElement -> {
                graphicElementMapper.partialUpdate(existingGraphicElement, graphicElementDTO);

                return existingGraphicElement;
            })
            .map(graphicElementRepository::save)
            .map(graphicElementMapper::toDto);
    }

    /**
     * Get all the graphicElements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GraphicElementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GraphicElements");
        return graphicElementRepository.findAll(pageable).map(graphicElementMapper::toDto);
    }

    /**
     *  Get all the graphicElements where Keys is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GraphicElementDTO> findAllWhereKeysIsNull() {
        log.debug("Request to get all graphicElements where Keys is null");
        return StreamSupport
            .stream(graphicElementRepository.findAll().spliterator(), false)
            .filter(graphicElement -> graphicElement.getKeys() == null)
            .map(graphicElementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one graphicElement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GraphicElementDTO> findOne(Long id) {
        log.debug("Request to get GraphicElement : {}", id);
        return graphicElementRepository.findById(id).map(graphicElementMapper::toDto);
    }

    /**
     * Delete the graphicElement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GraphicElement : {}", id);
        graphicElementRepository.deleteById(id);
    }
}
