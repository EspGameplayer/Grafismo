package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Match;
import grafismo.domain.MatchAction;
import grafismo.repository.MatchActionRepository;
import grafismo.service.MatchActionService;
import grafismo.service.dto.MatchActionDTO;
import grafismo.service.mapper.MatchActionMapper;
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
 * Integration tests for the {@link MatchActionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MatchActionResourceIT {

    private static final String DEFAULT_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_TIMESTAMP = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/match-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatchActionRepository matchActionRepository;

    @Mock
    private MatchActionRepository matchActionRepositoryMock;

    @Autowired
    private MatchActionMapper matchActionMapper;

    @Mock
    private MatchActionService matchActionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchActionMockMvc;

    private MatchAction matchAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchAction createEntity(EntityManager em) {
        MatchAction matchAction = new MatchAction().timestamp(DEFAULT_TIMESTAMP).details(DEFAULT_DETAILS).miscData(DEFAULT_MISC_DATA);
        // Add required entity
        Match match;
        if (TestUtil.findAll(em, Match.class).isEmpty()) {
            match = MatchResourceIT.createEntity(em);
            em.persist(match);
            em.flush();
        } else {
            match = TestUtil.findAll(em, Match.class).get(0);
        }
        matchAction.setMatch(match);
        return matchAction;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MatchAction createUpdatedEntity(EntityManager em) {
        MatchAction matchAction = new MatchAction().timestamp(UPDATED_TIMESTAMP).details(UPDATED_DETAILS).miscData(UPDATED_MISC_DATA);
        // Add required entity
        Match match;
        if (TestUtil.findAll(em, Match.class).isEmpty()) {
            match = MatchResourceIT.createUpdatedEntity(em);
            em.persist(match);
            em.flush();
        } else {
            match = TestUtil.findAll(em, Match.class).get(0);
        }
        matchAction.setMatch(match);
        return matchAction;
    }

    @BeforeEach
    public void initTest() {
        matchAction = createEntity(em);
    }

    @Test
    @Transactional
    void createMatchAction() throws Exception {
        int databaseSizeBeforeCreate = matchActionRepository.findAll().size();
        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);
        restMatchActionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeCreate + 1);
        MatchAction testMatchAction = matchActionList.get(matchActionList.size() - 1);
        assertThat(testMatchAction.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testMatchAction.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testMatchAction.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createMatchActionWithExistingId() throws Exception {
        // Create the MatchAction with an existing ID
        matchAction.setId(1L);
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        int databaseSizeBeforeCreate = matchActionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchActionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatchActions() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        // Get all the matchActionList
        restMatchActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matchAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMatchActionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(matchActionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatchActionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(matchActionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMatchActionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(matchActionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMatchActionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(matchActionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getMatchAction() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        // Get the matchAction
        restMatchActionMockMvc
            .perform(get(ENTITY_API_URL_ID, matchAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matchAction.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingMatchAction() throws Exception {
        // Get the matchAction
        restMatchActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMatchAction() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();

        // Update the matchAction
        MatchAction updatedMatchAction = matchActionRepository.findById(matchAction.getId()).get();
        // Disconnect from session so that the updates on updatedMatchAction are not directly saved in db
        em.detach(updatedMatchAction);
        updatedMatchAction.timestamp(UPDATED_TIMESTAMP).details(UPDATED_DETAILS).miscData(UPDATED_MISC_DATA);
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(updatedMatchAction);

        restMatchActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchActionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
        MatchAction testMatchAction = matchActionList.get(matchActionList.size() - 1);
        assertThat(testMatchAction.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatchAction.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testMatchAction.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, matchActionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchActionWithPatch() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();

        // Update the matchAction using partial update
        MatchAction partialUpdatedMatchAction = new MatchAction();
        partialUpdatedMatchAction.setId(matchAction.getId());

        partialUpdatedMatchAction.timestamp(UPDATED_TIMESTAMP).details(UPDATED_DETAILS);

        restMatchActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchAction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchAction))
            )
            .andExpect(status().isOk());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
        MatchAction testMatchAction = matchActionList.get(matchActionList.size() - 1);
        assertThat(testMatchAction.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatchAction.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testMatchAction.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateMatchActionWithPatch() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();

        // Update the matchAction using partial update
        MatchAction partialUpdatedMatchAction = new MatchAction();
        partialUpdatedMatchAction.setId(matchAction.getId());

        partialUpdatedMatchAction.timestamp(UPDATED_TIMESTAMP).details(UPDATED_DETAILS).miscData(UPDATED_MISC_DATA);

        restMatchActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatchAction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatchAction))
            )
            .andExpect(status().isOk());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
        MatchAction testMatchAction = matchActionList.get(matchActionList.size() - 1);
        assertThat(testMatchAction.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMatchAction.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testMatchAction.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, matchActionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatchAction() throws Exception {
        int databaseSizeBeforeUpdate = matchActionRepository.findAll().size();
        matchAction.setId(count.incrementAndGet());

        // Create the MatchAction
        MatchActionDTO matchActionDTO = matchActionMapper.toDto(matchAction);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchActionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(matchActionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MatchAction in the database
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatchAction() throws Exception {
        // Initialize the database
        matchActionRepository.saveAndFlush(matchAction);

        int databaseSizeBeforeDelete = matchActionRepository.findAll().size();

        // Delete the matchAction
        restMatchActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, matchAction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MatchAction> matchActionList = matchActionRepository.findAll();
        assertThat(matchActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
