package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Location;
import grafismo.repository.LocationRepository;
import grafismo.service.LocationService;
import grafismo.service.dto.LocationDTO;
import grafismo.service.mapper.LocationMapper;
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
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POPULATION = 1;
    private static final Integer UPDATED_POPULATION = 2;

    private static final Integer DEFAULT_CENSUS_YEAR = 1;
    private static final Integer UPDATED_CENSUS_YEAR = 2;

    private static final String DEFAULT_DENONYM = "AAAAAAAAAA";
    private static final String UPDATED_DENONYM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationRepository locationRepository;

    @Mock
    private LocationRepository locationRepositoryMock;

    @Autowired
    private LocationMapper locationMapper;

    @Mock
    private LocationService locationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .name(DEFAULT_NAME)
            .graphicsName(DEFAULT_GRAPHICS_NAME)
            .population(DEFAULT_POPULATION)
            .censusYear(DEFAULT_CENSUS_YEAR)
            .denonym(DEFAULT_DENONYM);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .population(UPDATED_POPULATION)
            .censusYear(UPDATED_CENSUS_YEAR)
            .denonym(UPDATED_DENONYM);
        return location;
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();
        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        restLocationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocation.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testLocation.getPopulation()).isEqualTo(DEFAULT_POPULATION);
        assertThat(testLocation.getCensusYear()).isEqualTo(DEFAULT_CENSUS_YEAR);
        assertThat(testLocation.getDenonym()).isEqualTo(DEFAULT_DENONYM);
    }

    @Test
    @Transactional
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = locationRepository.findAll().size();
        // set the field null
        location.setName(null);

        // Create the Location, which fails.
        LocationDTO locationDTO = locationMapper.toDto(location);

        restLocationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].graphicsName").value(hasItem(DEFAULT_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION)))
            .andExpect(jsonPath("$.[*].censusYear").value(hasItem(DEFAULT_CENSUS_YEAR)))
            .andExpect(jsonPath("$.[*].denonym").value(hasItem(DEFAULT_DENONYM)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLocationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(locationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(locationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLocationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(locationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(locationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.graphicsName").value(DEFAULT_GRAPHICS_NAME))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION))
            .andExpect(jsonPath("$.censusYear").value(DEFAULT_CENSUS_YEAR))
            .andExpect(jsonPath("$.denonym").value(DEFAULT_DENONYM));
    }

    @Test
    @Transactional
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .population(UPDATED_POPULATION)
            .censusYear(UPDATED_CENSUS_YEAR)
            .denonym(UPDATED_DENONYM);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocation.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testLocation.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testLocation.getCensusYear()).isEqualTo(UPDATED_CENSUS_YEAR);
        assertThat(testLocation.getDenonym()).isEqualTo(UPDATED_DENONYM);
    }

    @Test
    @Transactional
    void putNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation.name(UPDATED_NAME).population(UPDATED_POPULATION).censusYear(UPDATED_CENSUS_YEAR);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocation.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testLocation.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testLocation.getCensusYear()).isEqualTo(UPDATED_CENSUS_YEAR);
        assertThat(testLocation.getDenonym()).isEqualTo(DEFAULT_DENONYM);
    }

    @Test
    @Transactional
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .population(UPDATED_POPULATION)
            .censusYear(UPDATED_CENSUS_YEAR)
            .denonym(UPDATED_DENONYM);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocation.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testLocation.getPopulation()).isEqualTo(UPDATED_POPULATION);
        assertThat(testLocation.getCensusYear()).isEqualTo(UPDATED_CENSUS_YEAR);
        assertThat(testLocation.getDenonym()).isEqualTo(UPDATED_DENONYM);
    }

    @Test
    @Transactional
    void patchNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
