package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Venue;
import grafismo.repository.VenueRepository;
import grafismo.service.VenueService;
import grafismo.service.dto.VenueDTO;
import grafismo.service.mapper.VenueMapper;
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
 * Integration tests for the {@link VenueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VenueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LONG_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final String DEFAULT_OPENING_DATE = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_SIZE = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_ARTIFICIAL_GRASS = 1;
    private static final Integer UPDATED_IS_ARTIFICIAL_GRASS = 2;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_GEOGRAPHIC_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_GEOGRAPHIC_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/venues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VenueRepository venueRepository;

    @Mock
    private VenueRepository venueRepositoryMock;

    @Autowired
    private VenueMapper venueMapper;

    @Mock
    private VenueService venueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVenueMockMvc;

    private Venue venue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venue createEntity(EntityManager em) {
        Venue venue = new Venue()
            .name(DEFAULT_NAME)
            .graphicsName(DEFAULT_GRAPHICS_NAME)
            .longGraphicsName(DEFAULT_LONG_GRAPHICS_NAME)
            .capacity(DEFAULT_CAPACITY)
            .openingDate(DEFAULT_OPENING_DATE)
            .fieldSize(DEFAULT_FIELD_SIZE)
            .isArtificialGrass(DEFAULT_IS_ARTIFICIAL_GRASS)
            .address(DEFAULT_ADDRESS)
            .geographicLocation(DEFAULT_GEOGRAPHIC_LOCATION)
            .details(DEFAULT_DETAILS)
            .miscData(DEFAULT_MISC_DATA);
        return venue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venue createUpdatedEntity(EntityManager em) {
        Venue venue = new Venue()
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .capacity(UPDATED_CAPACITY)
            .openingDate(UPDATED_OPENING_DATE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .isArtificialGrass(UPDATED_IS_ARTIFICIAL_GRASS)
            .address(UPDATED_ADDRESS)
            .geographicLocation(UPDATED_GEOGRAPHIC_LOCATION)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        return venue;
    }

    @BeforeEach
    public void initTest() {
        venue = createEntity(em);
    }

    @Test
    @Transactional
    void createVenue() throws Exception {
        int databaseSizeBeforeCreate = venueRepository.findAll().size();
        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);
        restVenueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate + 1);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVenue.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testVenue.getLongGraphicsName()).isEqualTo(DEFAULT_LONG_GRAPHICS_NAME);
        assertThat(testVenue.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testVenue.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testVenue.getFieldSize()).isEqualTo(DEFAULT_FIELD_SIZE);
        assertThat(testVenue.getIsArtificialGrass()).isEqualTo(DEFAULT_IS_ARTIFICIAL_GRASS);
        assertThat(testVenue.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVenue.getGeographicLocation()).isEqualTo(DEFAULT_GEOGRAPHIC_LOCATION);
        assertThat(testVenue.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testVenue.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createVenueWithExistingId() throws Exception {
        // Create the Venue with an existing ID
        venue.setId(1L);
        VenueDTO venueDTO = venueMapper.toDto(venue);

        int databaseSizeBeforeCreate = venueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = venueRepository.findAll().size();
        // set the field null
        venue.setName(null);

        // Create the Venue, which fails.
        VenueDTO venueDTO = venueMapper.toDto(venue);

        restVenueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVenues() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        // Get all the venueList
        restVenueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].graphicsName").value(hasItem(DEFAULT_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].longGraphicsName").value(hasItem(DEFAULT_LONG_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE)))
            .andExpect(jsonPath("$.[*].fieldSize").value(hasItem(DEFAULT_FIELD_SIZE)))
            .andExpect(jsonPath("$.[*].isArtificialGrass").value(hasItem(DEFAULT_IS_ARTIFICIAL_GRASS)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].geographicLocation").value(hasItem(DEFAULT_GEOGRAPHIC_LOCATION)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVenuesWithEagerRelationshipsIsEnabled() throws Exception {
        when(venueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(venueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVenuesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(venueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(venueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        // Get the venue
        restVenueMockMvc
            .perform(get(ENTITY_API_URL_ID, venue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.graphicsName").value(DEFAULT_GRAPHICS_NAME))
            .andExpect(jsonPath("$.longGraphicsName").value(DEFAULT_LONG_GRAPHICS_NAME))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE))
            .andExpect(jsonPath("$.fieldSize").value(DEFAULT_FIELD_SIZE))
            .andExpect(jsonPath("$.isArtificialGrass").value(DEFAULT_IS_ARTIFICIAL_GRASS))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.geographicLocation").value(DEFAULT_GEOGRAPHIC_LOCATION))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingVenue() throws Exception {
        // Get the venue
        restVenueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeUpdate = venueRepository.findAll().size();

        // Update the venue
        Venue updatedVenue = venueRepository.findById(venue.getId()).get();
        // Disconnect from session so that the updates on updatedVenue are not directly saved in db
        em.detach(updatedVenue);
        updatedVenue
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .capacity(UPDATED_CAPACITY)
            .openingDate(UPDATED_OPENING_DATE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .isArtificialGrass(UPDATED_IS_ARTIFICIAL_GRASS)
            .address(UPDATED_ADDRESS)
            .geographicLocation(UPDATED_GEOGRAPHIC_LOCATION)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        VenueDTO venueDTO = venueMapper.toDto(updatedVenue);

        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVenue.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testVenue.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testVenue.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testVenue.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testVenue.getFieldSize()).isEqualTo(UPDATED_FIELD_SIZE);
        assertThat(testVenue.getIsArtificialGrass()).isEqualTo(UPDATED_IS_ARTIFICIAL_GRASS);
        assertThat(testVenue.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVenue.getGeographicLocation()).isEqualTo(UPDATED_GEOGRAPHIC_LOCATION);
        assertThat(testVenue.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testVenue.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVenueWithPatch() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeUpdate = venueRepository.findAll().size();

        // Update the venue using partial update
        Venue partialUpdatedVenue = new Venue();
        partialUpdatedVenue.setId(venue.getId());

        partialUpdatedVenue
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .capacity(UPDATED_CAPACITY)
            .isArtificialGrass(UPDATED_IS_ARTIFICIAL_GRASS)
            .details(UPDATED_DETAILS);

        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenue))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVenue.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testVenue.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testVenue.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testVenue.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testVenue.getFieldSize()).isEqualTo(DEFAULT_FIELD_SIZE);
        assertThat(testVenue.getIsArtificialGrass()).isEqualTo(UPDATED_IS_ARTIFICIAL_GRASS);
        assertThat(testVenue.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVenue.getGeographicLocation()).isEqualTo(DEFAULT_GEOGRAPHIC_LOCATION);
        assertThat(testVenue.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testVenue.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateVenueWithPatch() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeUpdate = venueRepository.findAll().size();

        // Update the venue using partial update
        Venue partialUpdatedVenue = new Venue();
        partialUpdatedVenue.setId(venue.getId());

        partialUpdatedVenue
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .capacity(UPDATED_CAPACITY)
            .openingDate(UPDATED_OPENING_DATE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .isArtificialGrass(UPDATED_IS_ARTIFICIAL_GRASS)
            .address(UPDATED_ADDRESS)
            .geographicLocation(UPDATED_GEOGRAPHIC_LOCATION)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);

        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenue))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
        Venue testVenue = venueList.get(venueList.size() - 1);
        assertThat(testVenue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVenue.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testVenue.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testVenue.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testVenue.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testVenue.getFieldSize()).isEqualTo(UPDATED_FIELD_SIZE);
        assertThat(testVenue.getIsArtificialGrass()).isEqualTo(UPDATED_IS_ARTIFICIAL_GRASS);
        assertThat(testVenue.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVenue.getGeographicLocation()).isEqualTo(UPDATED_GEOGRAPHIC_LOCATION);
        assertThat(testVenue.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testVenue.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVenue() throws Exception {
        int databaseSizeBeforeUpdate = venueRepository.findAll().size();
        venue.setId(count.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(venueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venue in the database
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVenue() throws Exception {
        // Initialize the database
        venueRepository.saveAndFlush(venue);

        int databaseSizeBeforeDelete = venueRepository.findAll().size();

        // Delete the venue
        restVenueMockMvc
            .perform(delete(ENTITY_API_URL_ID, venue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venue> venueList = venueRepository.findAll();
        assertThat(venueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
