package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Person;
import grafismo.domain.Player;
import grafismo.domain.enumeration.Side;
import grafismo.domain.enumeration.Side;
import grafismo.repository.PlayerRepository;
import grafismo.service.PlayerService;
import grafismo.service.dto.PlayerDTO;
import grafismo.service.mapper.PlayerMapper;
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
 * Integration tests for the {@link PlayerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlayerResourceIT {

    private static final String DEFAULT_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LONG_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHIRT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHIRT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Side DEFAULT_STRONGER_FOOT = Side.R;
    private static final Side UPDATED_STRONGER_FOOT = Side.L;

    private static final Side DEFAULT_PREFERRED_SIDE = Side.R;
    private static final Side UPDATED_PREFERRED_SIDE = Side.L;

    private static final String DEFAULT_CONTRACT_UNTIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_UNTIL = "BBBBBBBBBB";

    private static final String DEFAULT_RETIREMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RETIREMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlayerRepository playerRepository;

    @Mock
    private PlayerRepository playerRepositoryMock;

    @Autowired
    private PlayerMapper playerMapper;

    @Mock
    private PlayerService playerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerMockMvc;

    private Player player;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .graphicsName(DEFAULT_GRAPHICS_NAME)
            .longGraphicsName(DEFAULT_LONG_GRAPHICS_NAME)
            .shirtName(DEFAULT_SHIRT_NAME)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .strongerFoot(DEFAULT_STRONGER_FOOT)
            .preferredSide(DEFAULT_PREFERRED_SIDE)
            .contractUntil(DEFAULT_CONTRACT_UNTIL)
            .retirementDate(DEFAULT_RETIREMENT_DATE)
            .miscData(DEFAULT_MISC_DATA);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        player.setPerson(person);
        return player;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createUpdatedEntity(EntityManager em) {
        Player player = new Player()
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .shirtName(UPDATED_SHIRT_NAME)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .strongerFoot(UPDATED_STRONGER_FOOT)
            .preferredSide(UPDATED_PREFERRED_SIDE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        player.setPerson(person);
        return player;
    }

    @BeforeEach
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();
        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);
        restPlayerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testPlayer.getLongGraphicsName()).isEqualTo(DEFAULT_LONG_GRAPHICS_NAME);
        assertThat(testPlayer.getShirtName()).isEqualTo(DEFAULT_SHIRT_NAME);
        assertThat(testPlayer.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPlayer.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testPlayer.getStrongerFoot()).isEqualTo(DEFAULT_STRONGER_FOOT);
        assertThat(testPlayer.getPreferredSide()).isEqualTo(DEFAULT_PREFERRED_SIDE);
        assertThat(testPlayer.getContractUntil()).isEqualTo(DEFAULT_CONTRACT_UNTIL);
        assertThat(testPlayer.getRetirementDate()).isEqualTo(DEFAULT_RETIREMENT_DATE);
        assertThat(testPlayer.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createPlayerWithExistingId() throws Exception {
        // Create the Player with an existing ID
        player.setId(1L);
        PlayerDTO playerDTO = playerMapper.toDto(player);

        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGraphicsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setGraphicsName(null);

        // Create the Player, which fails.
        PlayerDTO playerDTO = playerMapper.toDto(player);

        restPlayerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].graphicsName").value(hasItem(DEFAULT_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].longGraphicsName").value(hasItem(DEFAULT_LONG_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].shirtName").value(hasItem(DEFAULT_SHIRT_NAME)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].strongerFoot").value(hasItem(DEFAULT_STRONGER_FOOT.toString())))
            .andExpect(jsonPath("$.[*].preferredSide").value(hasItem(DEFAULT_PREFERRED_SIDE.toString())))
            .andExpect(jsonPath("$.[*].contractUntil").value(hasItem(DEFAULT_CONTRACT_UNTIL)))
            .andExpect(jsonPath("$.[*].retirementDate").value(hasItem(DEFAULT_RETIREMENT_DATE)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlayersWithEagerRelationshipsIsEnabled() throws Exception {
        when(playerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlayerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(playerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlayersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(playerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlayerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(playerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.graphicsName").value(DEFAULT_GRAPHICS_NAME))
            .andExpect(jsonPath("$.longGraphicsName").value(DEFAULT_LONG_GRAPHICS_NAME))
            .andExpect(jsonPath("$.shirtName").value(DEFAULT_SHIRT_NAME))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.strongerFoot").value(DEFAULT_STRONGER_FOOT.toString()))
            .andExpect(jsonPath("$.preferredSide").value(DEFAULT_PREFERRED_SIDE.toString()))
            .andExpect(jsonPath("$.contractUntil").value(DEFAULT_CONTRACT_UNTIL))
            .andExpect(jsonPath("$.retirementDate").value(DEFAULT_RETIREMENT_DATE))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        // Disconnect from session so that the updates on updatedPlayer are not directly saved in db
        em.detach(updatedPlayer);
        updatedPlayer
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .shirtName(UPDATED_SHIRT_NAME)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .strongerFoot(UPDATED_STRONGER_FOOT)
            .preferredSide(UPDATED_PREFERRED_SIDE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);
        PlayerDTO playerDTO = playerMapper.toDto(updatedPlayer);

        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testPlayer.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testPlayer.getShirtName()).isEqualTo(UPDATED_SHIRT_NAME);
        assertThat(testPlayer.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPlayer.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPlayer.getStrongerFoot()).isEqualTo(UPDATED_STRONGER_FOOT);
        assertThat(testPlayer.getPreferredSide()).isEqualTo(UPDATED_PREFERRED_SIDE);
        assertThat(testPlayer.getContractUntil()).isEqualTo(UPDATED_CONTRACT_UNTIL);
        assertThat(testPlayer.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, playerDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .shirtName(UPDATED_SHIRT_NAME)
            .weight(UPDATED_WEIGHT)
            .strongerFoot(UPDATED_STRONGER_FOOT)
            .preferredSide(UPDATED_PREFERRED_SIDE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testPlayer.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testPlayer.getShirtName()).isEqualTo(UPDATED_SHIRT_NAME);
        assertThat(testPlayer.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testPlayer.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPlayer.getStrongerFoot()).isEqualTo(UPDATED_STRONGER_FOOT);
        assertThat(testPlayer.getPreferredSide()).isEqualTo(UPDATED_PREFERRED_SIDE);
        assertThat(testPlayer.getContractUntil()).isEqualTo(UPDATED_CONTRACT_UNTIL);
        assertThat(testPlayer.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .shirtName(UPDATED_SHIRT_NAME)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .strongerFoot(UPDATED_STRONGER_FOOT)
            .preferredSide(UPDATED_PREFERRED_SIDE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testPlayer.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testPlayer.getShirtName()).isEqualTo(UPDATED_SHIRT_NAME);
        assertThat(testPlayer.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testPlayer.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testPlayer.getStrongerFoot()).isEqualTo(UPDATED_STRONGER_FOOT);
        assertThat(testPlayer.getPreferredSide()).isEqualTo(UPDATED_PREFERRED_SIDE);
        assertThat(testPlayer.getContractUntil()).isEqualTo(UPDATED_CONTRACT_UNTIL);
        assertThat(testPlayer.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testPlayer.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, playerDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // Create the Player
        PlayerDTO playerDTO = playerMapper.toDto(player);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(playerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Delete the player
        restPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, player.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
