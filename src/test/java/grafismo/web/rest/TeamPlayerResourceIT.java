package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Player;
import grafismo.domain.TeamPlayer;
import grafismo.repository.TeamPlayerRepository;
import grafismo.service.TeamPlayerService;
import grafismo.service.dto.TeamPlayerDTO;
import grafismo.service.mapper.TeamPlayerMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TeamPlayerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TeamPlayerResourceIT {

    private static final Integer DEFAULT_PREFERRED_SHIRT_NUMBER = 1;
    private static final Integer UPDATED_PREFERRED_SHIRT_NUMBER = 2;

    private static final Integer DEFAULT_IS_YOUTH = 1;
    private static final Integer UPDATED_IS_YOUTH = 2;

    private static final LocalDate DEFAULT_SINCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SINCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UNTIL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UNTIL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/team-players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Mock
    private TeamPlayerRepository teamPlayerRepositoryMock;

    @Autowired
    private TeamPlayerMapper teamPlayerMapper;

    @Mock
    private TeamPlayerService teamPlayerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeamPlayerMockMvc;

    private TeamPlayer teamPlayer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamPlayer createEntity(EntityManager em) {
        TeamPlayer teamPlayer = new TeamPlayer()
            .preferredShirtNumber(DEFAULT_PREFERRED_SHIRT_NUMBER)
            .isYouth(DEFAULT_IS_YOUTH)
            .sinceDate(DEFAULT_SINCE_DATE)
            .untilDate(DEFAULT_UNTIL_DATE)
            .miscData(DEFAULT_MISC_DATA);
        // Add required entity
        Player player;
        if (TestUtil.findAll(em, Player.class).isEmpty()) {
            player = PlayerResourceIT.createEntity(em);
            em.persist(player);
            em.flush();
        } else {
            player = TestUtil.findAll(em, Player.class).get(0);
        }
        teamPlayer.setPlayer(player);
        return teamPlayer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamPlayer createUpdatedEntity(EntityManager em) {
        TeamPlayer teamPlayer = new TeamPlayer()
            .preferredShirtNumber(UPDATED_PREFERRED_SHIRT_NUMBER)
            .isYouth(UPDATED_IS_YOUTH)
            .sinceDate(UPDATED_SINCE_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .miscData(UPDATED_MISC_DATA);
        // Add required entity
        Player player;
        if (TestUtil.findAll(em, Player.class).isEmpty()) {
            player = PlayerResourceIT.createUpdatedEntity(em);
            em.persist(player);
            em.flush();
        } else {
            player = TestUtil.findAll(em, Player.class).get(0);
        }
        teamPlayer.setPlayer(player);
        return teamPlayer;
    }

    @BeforeEach
    public void initTest() {
        teamPlayer = createEntity(em);
    }

    @Test
    @Transactional
    void createTeamPlayer() throws Exception {
        int databaseSizeBeforeCreate = teamPlayerRepository.findAll().size();
        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);
        restTeamPlayerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.getPreferredShirtNumber()).isEqualTo(DEFAULT_PREFERRED_SHIRT_NUMBER);
        assertThat(testTeamPlayer.getIsYouth()).isEqualTo(DEFAULT_IS_YOUTH);
        assertThat(testTeamPlayer.getSinceDate()).isEqualTo(DEFAULT_SINCE_DATE);
        assertThat(testTeamPlayer.getUntilDate()).isEqualTo(DEFAULT_UNTIL_DATE);
        assertThat(testTeamPlayer.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createTeamPlayerWithExistingId() throws Exception {
        // Create the TeamPlayer with an existing ID
        teamPlayer.setId(1L);
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        int databaseSizeBeforeCreate = teamPlayerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamPlayerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTeamPlayers() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get all the teamPlayerList
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamPlayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferredShirtNumber").value(hasItem(DEFAULT_PREFERRED_SHIRT_NUMBER)))
            .andExpect(jsonPath("$.[*].isYouth").value(hasItem(DEFAULT_IS_YOUTH)))
            .andExpect(jsonPath("$.[*].sinceDate").value(hasItem(DEFAULT_SINCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].untilDate").value(hasItem(DEFAULT_UNTIL_DATE.toString())))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamPlayersWithEagerRelationshipsIsEnabled() throws Exception {
        when(teamPlayerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamPlayerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teamPlayerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTeamPlayersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(teamPlayerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTeamPlayerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(teamPlayerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        // Get the teamPlayer
        restTeamPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, teamPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamPlayer.getId().intValue()))
            .andExpect(jsonPath("$.preferredShirtNumber").value(DEFAULT_PREFERRED_SHIRT_NUMBER))
            .andExpect(jsonPath("$.isYouth").value(DEFAULT_IS_YOUTH))
            .andExpect(jsonPath("$.sinceDate").value(DEFAULT_SINCE_DATE.toString()))
            .andExpect(jsonPath("$.untilDate").value(DEFAULT_UNTIL_DATE.toString()))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingTeamPlayer() throws Exception {
        // Get the teamPlayer
        restTeamPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer
        TeamPlayer updatedTeamPlayer = teamPlayerRepository.findById(teamPlayer.getId()).get();
        // Disconnect from session so that the updates on updatedTeamPlayer are not directly saved in db
        em.detach(updatedTeamPlayer);
        updatedTeamPlayer
            .preferredShirtNumber(UPDATED_PREFERRED_SHIRT_NUMBER)
            .isYouth(UPDATED_IS_YOUTH)
            .sinceDate(UPDATED_SINCE_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .miscData(UPDATED_MISC_DATA);
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(updatedTeamPlayer);

        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.getPreferredShirtNumber()).isEqualTo(UPDATED_PREFERRED_SHIRT_NUMBER);
        assertThat(testTeamPlayer.getIsYouth()).isEqualTo(UPDATED_IS_YOUTH);
        assertThat(testTeamPlayer.getSinceDate()).isEqualTo(UPDATED_SINCE_DATE);
        assertThat(testTeamPlayer.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testTeamPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTeamPlayerWithPatch() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer using partial update
        TeamPlayer partialUpdatedTeamPlayer = new TeamPlayer();
        partialUpdatedTeamPlayer.setId(teamPlayer.getId());

        partialUpdatedTeamPlayer
            .preferredShirtNumber(UPDATED_PREFERRED_SHIRT_NUMBER)
            .isYouth(UPDATED_IS_YOUTH)
            .sinceDate(UPDATED_SINCE_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .miscData(UPDATED_MISC_DATA);

        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamPlayer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamPlayer))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.getPreferredShirtNumber()).isEqualTo(UPDATED_PREFERRED_SHIRT_NUMBER);
        assertThat(testTeamPlayer.getIsYouth()).isEqualTo(UPDATED_IS_YOUTH);
        assertThat(testTeamPlayer.getSinceDate()).isEqualTo(UPDATED_SINCE_DATE);
        assertThat(testTeamPlayer.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testTeamPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateTeamPlayerWithPatch() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();

        // Update the teamPlayer using partial update
        TeamPlayer partialUpdatedTeamPlayer = new TeamPlayer();
        partialUpdatedTeamPlayer.setId(teamPlayer.getId());

        partialUpdatedTeamPlayer
            .preferredShirtNumber(UPDATED_PREFERRED_SHIRT_NUMBER)
            .isYouth(UPDATED_IS_YOUTH)
            .sinceDate(UPDATED_SINCE_DATE)
            .untilDate(UPDATED_UNTIL_DATE)
            .miscData(UPDATED_MISC_DATA);

        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTeamPlayer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTeamPlayer))
            )
            .andExpect(status().isOk());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
        TeamPlayer testTeamPlayer = teamPlayerList.get(teamPlayerList.size() - 1);
        assertThat(testTeamPlayer.getPreferredShirtNumber()).isEqualTo(UPDATED_PREFERRED_SHIRT_NUMBER);
        assertThat(testTeamPlayer.getIsYouth()).isEqualTo(UPDATED_IS_YOUTH);
        assertThat(testTeamPlayer.getSinceDate()).isEqualTo(UPDATED_SINCE_DATE);
        assertThat(testTeamPlayer.getUntilDate()).isEqualTo(UPDATED_UNTIL_DATE);
        assertThat(testTeamPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, teamPlayerDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTeamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = teamPlayerRepository.findAll().size();
        teamPlayer.setId(count.incrementAndGet());

        // Create the TeamPlayer
        TeamPlayerDTO teamPlayerDTO = teamPlayerMapper.toDto(teamPlayer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTeamPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(teamPlayerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TeamPlayer in the database
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTeamPlayer() throws Exception {
        // Initialize the database
        teamPlayerRepository.saveAndFlush(teamPlayer);

        int databaseSizeBeforeDelete = teamPlayerRepository.findAll().size();

        // Delete the teamPlayer
        restTeamPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, teamPlayer.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamPlayer> teamPlayerList = teamPlayerRepository.findAll();
        assertThat(teamPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
