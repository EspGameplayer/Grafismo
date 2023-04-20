package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Person;
import grafismo.repository.PersonRepository;
import grafismo.service.PersonService;
import grafismo.service.dto.PersonDTO;
import grafismo.service.mapper.PersonMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NICKNAMES = "AAAAAAAAAA";
    private static final String UPDATED_NICKNAMES = "BBBBBBBBBB";

    private static final String DEFAULT_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LONG_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CALLNAMES = "AAAAAAAAAA";
    private static final String UPDATED_CALLNAMES = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DEATH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DEATH_DATE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MEDIUM_SHOT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIUM_SHOT_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MEDIUM_SHOT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FULL_SHOT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FULL_SHOT_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FULL_SHOT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SOCIAL_MEDIA = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_MEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonRepository personRepository;

    @Mock
    private PersonRepository personRepositoryMock;

    @Autowired
    private PersonMapper personMapper;

    @Mock
    private PersonService personServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .name(DEFAULT_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .surname1(DEFAULT_SURNAME_1)
            .surname2(DEFAULT_SURNAME_2)
            .nicknames(DEFAULT_NICKNAMES)
            .graphicsName(DEFAULT_GRAPHICS_NAME)
            .longGraphicsName(DEFAULT_LONG_GRAPHICS_NAME)
            .callnames(DEFAULT_CALLNAMES)
            .birthDate(DEFAULT_BIRTH_DATE)
            .deathDate(DEFAULT_DEATH_DATE)
            .mediumShotPhoto(DEFAULT_MEDIUM_SHOT_PHOTO)
            .mediumShotPhotoContentType(DEFAULT_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)
            .fullShotPhoto(DEFAULT_FULL_SHOT_PHOTO)
            .fullShotPhotoContentType(DEFAULT_FULL_SHOT_PHOTO_CONTENT_TYPE)
            .socialMedia(DEFAULT_SOCIAL_MEDIA)
            .details(DEFAULT_DETAILS)
            .miscData(DEFAULT_MISC_DATA);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .name(UPDATED_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .surname1(UPDATED_SURNAME_1)
            .surname2(UPDATED_SURNAME_2)
            .nicknames(UPDATED_NICKNAMES)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .callnames(UPDATED_CALLNAMES)
            .birthDate(UPDATED_BIRTH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .mediumShotPhoto(UPDATED_MEDIUM_SHOT_PHOTO)
            .mediumShotPhotoContentType(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)
            .fullShotPhoto(UPDATED_FULL_SHOT_PHOTO)
            .fullShotPhotoContentType(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE)
            .socialMedia(UPDATED_SOCIAL_MEDIA)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPerson.getSurname1()).isEqualTo(DEFAULT_SURNAME_1);
        assertThat(testPerson.getSurname2()).isEqualTo(DEFAULT_SURNAME_2);
        assertThat(testPerson.getNicknames()).isEqualTo(DEFAULT_NICKNAMES);
        assertThat(testPerson.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testPerson.getLongGraphicsName()).isEqualTo(DEFAULT_LONG_GRAPHICS_NAME);
        assertThat(testPerson.getCallnames()).isEqualTo(DEFAULT_CALLNAMES);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(DEFAULT_DEATH_DATE);
        assertThat(testPerson.getMediumShotPhoto()).isEqualTo(DEFAULT_MEDIUM_SHOT_PHOTO);
        assertThat(testPerson.getMediumShotPhotoContentType()).isEqualTo(DEFAULT_MEDIUM_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getFullShotPhoto()).isEqualTo(DEFAULT_FULL_SHOT_PHOTO);
        assertThat(testPerson.getFullShotPhotoContentType()).isEqualTo(DEFAULT_FULL_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getSocialMedia()).isEqualTo(DEFAULT_SOCIAL_MEDIA);
        assertThat(testPerson.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testPerson.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGraphicsNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setGraphicsName(null);

        // Create the Person, which fails.
        PersonDTO personDTO = personMapper.toDto(person);

        restPersonMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].surname1").value(hasItem(DEFAULT_SURNAME_1)))
            .andExpect(jsonPath("$.[*].surname2").value(hasItem(DEFAULT_SURNAME_2)))
            .andExpect(jsonPath("$.[*].nicknames").value(hasItem(DEFAULT_NICKNAMES)))
            .andExpect(jsonPath("$.[*].graphicsName").value(hasItem(DEFAULT_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].longGraphicsName").value(hasItem(DEFAULT_LONG_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].callnames").value(hasItem(DEFAULT_CALLNAMES)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].deathDate").value(hasItem(DEFAULT_DEATH_DATE)))
            .andExpect(jsonPath("$.[*].mediumShotPhotoContentType").value(hasItem(DEFAULT_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mediumShotPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIUM_SHOT_PHOTO))))
            .andExpect(jsonPath("$.[*].fullShotPhotoContentType").value(hasItem(DEFAULT_FULL_SHOT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fullShotPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FULL_SHOT_PHOTO))))
            .andExpect(jsonPath("$.[*].socialMedia").value(hasItem(DEFAULT_SOCIAL_MEDIA)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPeopleWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(personServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPersonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(personServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.surname1").value(DEFAULT_SURNAME_1))
            .andExpect(jsonPath("$.surname2").value(DEFAULT_SURNAME_2))
            .andExpect(jsonPath("$.nicknames").value(DEFAULT_NICKNAMES))
            .andExpect(jsonPath("$.graphicsName").value(DEFAULT_GRAPHICS_NAME))
            .andExpect(jsonPath("$.longGraphicsName").value(DEFAULT_LONG_GRAPHICS_NAME))
            .andExpect(jsonPath("$.callnames").value(DEFAULT_CALLNAMES))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
            .andExpect(jsonPath("$.deathDate").value(DEFAULT_DEATH_DATE))
            .andExpect(jsonPath("$.mediumShotPhotoContentType").value(DEFAULT_MEDIUM_SHOT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediumShotPhoto").value(Base64Utils.encodeToString(DEFAULT_MEDIUM_SHOT_PHOTO)))
            .andExpect(jsonPath("$.fullShotPhotoContentType").value(DEFAULT_FULL_SHOT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.fullShotPhoto").value(Base64Utils.encodeToString(DEFAULT_FULL_SHOT_PHOTO)))
            .andExpect(jsonPath("$.socialMedia").value(DEFAULT_SOCIAL_MEDIA))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .name(UPDATED_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .surname1(UPDATED_SURNAME_1)
            .surname2(UPDATED_SURNAME_2)
            .nicknames(UPDATED_NICKNAMES)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .callnames(UPDATED_CALLNAMES)
            .birthDate(UPDATED_BIRTH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .mediumShotPhoto(UPDATED_MEDIUM_SHOT_PHOTO)
            .mediumShotPhotoContentType(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)
            .fullShotPhoto(UPDATED_FULL_SHOT_PHOTO)
            .fullShotPhotoContentType(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE)
            .socialMedia(UPDATED_SOCIAL_MEDIA)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPerson.getSurname1()).isEqualTo(UPDATED_SURNAME_1);
        assertThat(testPerson.getSurname2()).isEqualTo(UPDATED_SURNAME_2);
        assertThat(testPerson.getNicknames()).isEqualTo(UPDATED_NICKNAMES);
        assertThat(testPerson.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testPerson.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testPerson.getCallnames()).isEqualTo(UPDATED_CALLNAMES);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(UPDATED_DEATH_DATE);
        assertThat(testPerson.getMediumShotPhoto()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO);
        assertThat(testPerson.getMediumShotPhotoContentType()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getFullShotPhoto()).isEqualTo(UPDATED_FULL_SHOT_PHOTO);
        assertThat(testPerson.getFullShotPhotoContentType()).isEqualTo(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getSocialMedia()).isEqualTo(UPDATED_SOCIAL_MEDIA);
        assertThat(testPerson.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testPerson.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .surname1(UPDATED_SURNAME_1)
            .callnames(UPDATED_CALLNAMES)
            .birthDate(UPDATED_BIRTH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .mediumShotPhoto(UPDATED_MEDIUM_SHOT_PHOTO)
            .mediumShotPhotoContentType(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)
            .fullShotPhoto(UPDATED_FULL_SHOT_PHOTO)
            .fullShotPhotoContentType(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE)
            .socialMedia(UPDATED_SOCIAL_MEDIA);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testPerson.getSurname1()).isEqualTo(UPDATED_SURNAME_1);
        assertThat(testPerson.getSurname2()).isEqualTo(DEFAULT_SURNAME_2);
        assertThat(testPerson.getNicknames()).isEqualTo(DEFAULT_NICKNAMES);
        assertThat(testPerson.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testPerson.getLongGraphicsName()).isEqualTo(DEFAULT_LONG_GRAPHICS_NAME);
        assertThat(testPerson.getCallnames()).isEqualTo(UPDATED_CALLNAMES);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(UPDATED_DEATH_DATE);
        assertThat(testPerson.getMediumShotPhoto()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO);
        assertThat(testPerson.getMediumShotPhotoContentType()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getFullShotPhoto()).isEqualTo(UPDATED_FULL_SHOT_PHOTO);
        assertThat(testPerson.getFullShotPhotoContentType()).isEqualTo(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getSocialMedia()).isEqualTo(UPDATED_SOCIAL_MEDIA);
        assertThat(testPerson.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testPerson.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .name(UPDATED_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .surname1(UPDATED_SURNAME_1)
            .surname2(UPDATED_SURNAME_2)
            .nicknames(UPDATED_NICKNAMES)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .longGraphicsName(UPDATED_LONG_GRAPHICS_NAME)
            .callnames(UPDATED_CALLNAMES)
            .birthDate(UPDATED_BIRTH_DATE)
            .deathDate(UPDATED_DEATH_DATE)
            .mediumShotPhoto(UPDATED_MEDIUM_SHOT_PHOTO)
            .mediumShotPhotoContentType(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE)
            .fullShotPhoto(UPDATED_FULL_SHOT_PHOTO)
            .fullShotPhotoContentType(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE)
            .socialMedia(UPDATED_SOCIAL_MEDIA)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testPerson.getSurname1()).isEqualTo(UPDATED_SURNAME_1);
        assertThat(testPerson.getSurname2()).isEqualTo(UPDATED_SURNAME_2);
        assertThat(testPerson.getNicknames()).isEqualTo(UPDATED_NICKNAMES);
        assertThat(testPerson.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testPerson.getLongGraphicsName()).isEqualTo(UPDATED_LONG_GRAPHICS_NAME);
        assertThat(testPerson.getCallnames()).isEqualTo(UPDATED_CALLNAMES);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getDeathDate()).isEqualTo(UPDATED_DEATH_DATE);
        assertThat(testPerson.getMediumShotPhoto()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO);
        assertThat(testPerson.getMediumShotPhotoContentType()).isEqualTo(UPDATED_MEDIUM_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getFullShotPhoto()).isEqualTo(UPDATED_FULL_SHOT_PHOTO);
        assertThat(testPerson.getFullShotPhotoContentType()).isEqualTo(UPDATED_FULL_SHOT_PHOTO_CONTENT_TYPE);
        assertThat(testPerson.getSocialMedia()).isEqualTo(UPDATED_SOCIAL_MEDIA);
        assertThat(testPerson.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testPerson.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
