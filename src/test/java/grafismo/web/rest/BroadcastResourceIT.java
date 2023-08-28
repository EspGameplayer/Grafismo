package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Broadcast;
import grafismo.domain.Match;
import grafismo.domain.SystemConfiguration;
import grafismo.repository.BroadcastRepository;
import grafismo.service.BroadcastService;
import grafismo.service.dto.BroadcastDTO;
import grafismo.service.mapper.BroadcastMapper;
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
 * Integration tests for the {@link BroadcastResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BroadcastResourceIT {

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/broadcasts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BroadcastRepository broadcastRepository;

    @Mock
    private BroadcastRepository broadcastRepositoryMock;

    @Autowired
    private BroadcastMapper broadcastMapper;

    @Mock
    private BroadcastService broadcastServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBroadcastMockMvc;

    private Broadcast broadcast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Broadcast createEntity(EntityManager em) {
        Broadcast broadcast = new Broadcast().miscData(DEFAULT_MISC_DATA);
        // Add required entity
        Match match;
        if (TestUtil.findAll(em, Match.class).isEmpty()) {
            match = MatchResourceIT.createEntity(em);
            em.persist(match);
            em.flush();
        } else {
            match = TestUtil.findAll(em, Match.class).get(0);
        }
        broadcast.setMatch(match);
        // Add required entity
        SystemConfiguration systemConfiguration;
        if (TestUtil.findAll(em, SystemConfiguration.class).isEmpty()) {
            systemConfiguration = SystemConfigurationResourceIT.createEntity(em);
            em.persist(systemConfiguration);
            em.flush();
        } else {
            systemConfiguration = TestUtil.findAll(em, SystemConfiguration.class).get(0);
        }
        broadcast.setSystemConfiguration(systemConfiguration);
        return broadcast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Broadcast createUpdatedEntity(EntityManager em) {
        Broadcast broadcast = new Broadcast().miscData(UPDATED_MISC_DATA);
        // Add required entity
        Match match;
        if (TestUtil.findAll(em, Match.class).isEmpty()) {
            match = MatchResourceIT.createUpdatedEntity(em);
            em.persist(match);
            em.flush();
        } else {
            match = TestUtil.findAll(em, Match.class).get(0);
        }
        broadcast.setMatch(match);
        // Add required entity
        SystemConfiguration systemConfiguration;
        if (TestUtil.findAll(em, SystemConfiguration.class).isEmpty()) {
            systemConfiguration = SystemConfigurationResourceIT.createUpdatedEntity(em);
            em.persist(systemConfiguration);
            em.flush();
        } else {
            systemConfiguration = TestUtil.findAll(em, SystemConfiguration.class).get(0);
        }
        broadcast.setSystemConfiguration(systemConfiguration);
        return broadcast;
    }

    @BeforeEach
    public void initTest() {
        broadcast = createEntity(em);
    }

    @Test
    @Transactional
    void createBroadcast() throws Exception {
        int databaseSizeBeforeCreate = broadcastRepository.findAll().size();
        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);
        restBroadcastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeCreate + 1);
        Broadcast testBroadcast = broadcastList.get(broadcastList.size() - 1);
        assertThat(testBroadcast.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createBroadcastWithExistingId() throws Exception {
        // Create the Broadcast with an existing ID
        broadcast.setId(1L);
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        int databaseSizeBeforeCreate = broadcastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBroadcastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBroadcasts() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        // Get all the broadcastList
        restBroadcastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(broadcast.getId().intValue())))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBroadcastsWithEagerRelationshipsIsEnabled() throws Exception {
        when(broadcastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBroadcastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(broadcastServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBroadcastsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(broadcastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBroadcastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(broadcastServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBroadcast() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        // Get the broadcast
        restBroadcastMockMvc
            .perform(get(ENTITY_API_URL_ID, broadcast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(broadcast.getId().intValue()))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingBroadcast() throws Exception {
        // Get the broadcast
        restBroadcastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBroadcast() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();

        // Update the broadcast
        Broadcast updatedBroadcast = broadcastRepository.findById(broadcast.getId()).get();
        // Disconnect from session so that the updates on updatedBroadcast are not directly saved in db
        em.detach(updatedBroadcast);
        updatedBroadcast.miscData(UPDATED_MISC_DATA);
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(updatedBroadcast);

        restBroadcastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, broadcastDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isOk());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
        Broadcast testBroadcast = broadcastList.get(broadcastList.size() - 1);
        assertThat(testBroadcast.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, broadcastDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBroadcastWithPatch() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();

        // Update the broadcast using partial update
        Broadcast partialUpdatedBroadcast = new Broadcast();
        partialUpdatedBroadcast.setId(broadcast.getId());

        partialUpdatedBroadcast.miscData(UPDATED_MISC_DATA);

        restBroadcastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBroadcast.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBroadcast))
            )
            .andExpect(status().isOk());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
        Broadcast testBroadcast = broadcastList.get(broadcastList.size() - 1);
        assertThat(testBroadcast.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateBroadcastWithPatch() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();

        // Update the broadcast using partial update
        Broadcast partialUpdatedBroadcast = new Broadcast();
        partialUpdatedBroadcast.setId(broadcast.getId());

        partialUpdatedBroadcast.miscData(UPDATED_MISC_DATA);

        restBroadcastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBroadcast.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBroadcast))
            )
            .andExpect(status().isOk());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
        Broadcast testBroadcast = broadcastList.get(broadcastList.size() - 1);
        assertThat(testBroadcast.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, broadcastDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBroadcast() throws Exception {
        int databaseSizeBeforeUpdate = broadcastRepository.findAll().size();
        broadcast.setId(count.incrementAndGet());

        // Create the Broadcast
        BroadcastDTO broadcastDTO = broadcastMapper.toDto(broadcast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Broadcast in the database
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBroadcast() throws Exception {
        // Initialize the database
        broadcastRepository.saveAndFlush(broadcast);

        int databaseSizeBeforeDelete = broadcastRepository.findAll().size();

        // Delete the broadcast
        restBroadcastMockMvc
            .perform(delete(ENTITY_API_URL_ID, broadcast.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Broadcast> broadcastList = broadcastRepository.findAll();
        assertThat(broadcastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
