package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Formation;
import grafismo.domain.TemplateFormation;
import grafismo.repository.TemplateFormationRepository;
import grafismo.service.TemplateFormationService;
import grafismo.service.dto.TemplateFormationDTO;
import grafismo.service.mapper.TemplateFormationMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TemplateFormationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TemplateFormationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/template-formations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateFormationRepository templateFormationRepository;

    @Mock
    private TemplateFormationRepository templateFormationRepositoryMock;

    @Autowired
    private TemplateFormationMapper templateFormationMapper;

    @Mock
    private TemplateFormationService templateFormationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateFormationMockMvc;

    private TemplateFormation templateFormation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFormation createEntity(EntityManager em) {
        TemplateFormation templateFormation = new TemplateFormation().name(DEFAULT_NAME);
        // Add required entity
        Formation formation;
        if (TestUtil.findAll(em, Formation.class).isEmpty()) {
            formation = FormationResourceIT.createEntity(em);
            em.persist(formation);
            em.flush();
        } else {
            formation = TestUtil.findAll(em, Formation.class).get(0);
        }
        templateFormation.setFormation(formation);
        return templateFormation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFormation createUpdatedEntity(EntityManager em) {
        TemplateFormation templateFormation = new TemplateFormation().name(UPDATED_NAME);
        // Add required entity
        Formation formation;
        if (TestUtil.findAll(em, Formation.class).isEmpty()) {
            formation = FormationResourceIT.createUpdatedEntity(em);
            em.persist(formation);
            em.flush();
        } else {
            formation = TestUtil.findAll(em, Formation.class).get(0);
        }
        templateFormation.setFormation(formation);
        return templateFormation;
    }

    @BeforeEach
    public void initTest() {
        templateFormation = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateFormation() throws Exception {
        int databaseSizeBeforeCreate = templateFormationRepository.findAll().size();
        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);
        restTemplateFormationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateFormation testTemplateFormation = templateFormationList.get(templateFormationList.size() - 1);
        assertThat(testTemplateFormation.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTemplateFormationWithExistingId() throws Exception {
        // Create the TemplateFormation with an existing ID
        templateFormation.setId(1L);
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        int databaseSizeBeforeCreate = templateFormationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateFormationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFormationRepository.findAll().size();
        // set the field null
        templateFormation.setName(null);

        // Create the TemplateFormation, which fails.
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        restTemplateFormationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTemplateFormations() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        // Get all the templateFormationList
        restTemplateFormationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateFormation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplateFormationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(templateFormationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplateFormationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(templateFormationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplateFormationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(templateFormationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplateFormationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(templateFormationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTemplateFormation() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        // Get the templateFormation
        restTemplateFormationMockMvc
            .perform(get(ENTITY_API_URL_ID, templateFormation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateFormation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTemplateFormation() throws Exception {
        // Get the templateFormation
        restTemplateFormationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTemplateFormation() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();

        // Update the templateFormation
        TemplateFormation updatedTemplateFormation = templateFormationRepository.findById(templateFormation.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateFormation are not directly saved in db
        em.detach(updatedTemplateFormation);
        updatedTemplateFormation.name(UPDATED_NAME);
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(updatedTemplateFormation);

        restTemplateFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateFormationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
        TemplateFormation testTemplateFormation = templateFormationList.get(templateFormationList.size() - 1);
        assertThat(testTemplateFormation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateFormationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateFormationWithPatch() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();

        // Update the templateFormation using partial update
        TemplateFormation partialUpdatedTemplateFormation = new TemplateFormation();
        partialUpdatedTemplateFormation.setId(templateFormation.getId());

        partialUpdatedTemplateFormation.name(UPDATED_NAME);

        restTemplateFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateFormation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateFormation))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
        TemplateFormation testTemplateFormation = templateFormationList.get(templateFormationList.size() - 1);
        assertThat(testTemplateFormation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTemplateFormationWithPatch() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();

        // Update the templateFormation using partial update
        TemplateFormation partialUpdatedTemplateFormation = new TemplateFormation();
        partialUpdatedTemplateFormation.setId(templateFormation.getId());

        partialUpdatedTemplateFormation.name(UPDATED_NAME);

        restTemplateFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateFormation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateFormation))
            )
            .andExpect(status().isOk());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
        TemplateFormation testTemplateFormation = templateFormationList.get(templateFormationList.size() - 1);
        assertThat(testTemplateFormation.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateFormationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateFormation() throws Exception {
        int databaseSizeBeforeUpdate = templateFormationRepository.findAll().size();
        templateFormation.setId(count.incrementAndGet());

        // Create the TemplateFormation
        TemplateFormationDTO templateFormationDTO = templateFormationMapper.toDto(templateFormation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateFormationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateFormationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateFormation in the database
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateFormation() throws Exception {
        // Initialize the database
        templateFormationRepository.saveAndFlush(templateFormation);

        int databaseSizeBeforeDelete = templateFormationRepository.findAll().size();

        // Delete the templateFormation
        restTemplateFormationMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateFormation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateFormation> templateFormationList = templateFormationRepository.findAll();
        assertThat(templateFormationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
