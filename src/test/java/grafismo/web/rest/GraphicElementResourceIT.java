package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.GraphicElement;
import grafismo.repository.GraphicElementRepository;
import grafismo.service.dto.GraphicElementDTO;
import grafismo.service.mapper.GraphicElementMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GraphicElementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GraphicElementResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/graphic-elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GraphicElementRepository graphicElementRepository;

    @Autowired
    private GraphicElementMapper graphicElementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGraphicElementMockMvc;

    private GraphicElement graphicElement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GraphicElement createEntity(EntityManager em) {
        GraphicElement graphicElement = new GraphicElement().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return graphicElement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GraphicElement createUpdatedEntity(EntityManager em) {
        GraphicElement graphicElement = new GraphicElement().name(UPDATED_NAME).code(UPDATED_CODE);
        return graphicElement;
    }

    @BeforeEach
    public void initTest() {
        graphicElement = createEntity(em);
    }

    @Test
    @Transactional
    void createGraphicElement() throws Exception {
        int databaseSizeBeforeCreate = graphicElementRepository.findAll().size();
        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);
        restGraphicElementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeCreate + 1);
        GraphicElement testGraphicElement = graphicElementList.get(graphicElementList.size() - 1);
        assertThat(testGraphicElement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGraphicElement.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createGraphicElementWithExistingId() throws Exception {
        // Create the GraphicElement with an existing ID
        graphicElement.setId(1L);
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        int databaseSizeBeforeCreate = graphicElementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraphicElementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = graphicElementRepository.findAll().size();
        // set the field null
        graphicElement.setName(null);

        // Create the GraphicElement, which fails.
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        restGraphicElementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGraphicElements() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        // Get all the graphicElementList
        restGraphicElementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graphicElement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getGraphicElement() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        // Get the graphicElement
        restGraphicElementMockMvc
            .perform(get(ENTITY_API_URL_ID, graphicElement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(graphicElement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingGraphicElement() throws Exception {
        // Get the graphicElement
        restGraphicElementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGraphicElement() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();

        // Update the graphicElement
        GraphicElement updatedGraphicElement = graphicElementRepository.findById(graphicElement.getId()).get();
        // Disconnect from session so that the updates on updatedGraphicElement are not directly saved in db
        em.detach(updatedGraphicElement);
        updatedGraphicElement.name(UPDATED_NAME).code(UPDATED_CODE);
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(updatedGraphicElement);

        restGraphicElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, graphicElementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isOk());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
        GraphicElement testGraphicElement = graphicElementList.get(graphicElementList.size() - 1);
        assertThat(testGraphicElement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGraphicElement.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, graphicElementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGraphicElementWithPatch() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();

        // Update the graphicElement using partial update
        GraphicElement partialUpdatedGraphicElement = new GraphicElement();
        partialUpdatedGraphicElement.setId(graphicElement.getId());

        restGraphicElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGraphicElement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGraphicElement))
            )
            .andExpect(status().isOk());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
        GraphicElement testGraphicElement = graphicElementList.get(graphicElementList.size() - 1);
        assertThat(testGraphicElement.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGraphicElement.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateGraphicElementWithPatch() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();

        // Update the graphicElement using partial update
        GraphicElement partialUpdatedGraphicElement = new GraphicElement();
        partialUpdatedGraphicElement.setId(graphicElement.getId());

        partialUpdatedGraphicElement.name(UPDATED_NAME).code(UPDATED_CODE);

        restGraphicElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGraphicElement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGraphicElement))
            )
            .andExpect(status().isOk());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
        GraphicElement testGraphicElement = graphicElementList.get(graphicElementList.size() - 1);
        assertThat(testGraphicElement.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGraphicElement.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, graphicElementDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGraphicElement() throws Exception {
        int databaseSizeBeforeUpdate = graphicElementRepository.findAll().size();
        graphicElement.setId(count.incrementAndGet());

        // Create the GraphicElement
        GraphicElementDTO graphicElementDTO = graphicElementMapper.toDto(graphicElement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGraphicElementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(graphicElementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GraphicElement in the database
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGraphicElement() throws Exception {
        // Initialize the database
        graphicElementRepository.saveAndFlush(graphicElement);

        int databaseSizeBeforeDelete = graphicElementRepository.findAll().size();

        // Delete the graphicElement
        restGraphicElementMockMvc
            .perform(delete(ENTITY_API_URL_ID, graphicElement.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GraphicElement> graphicElementList = graphicElementRepository.findAll();
        assertThat(graphicElementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
