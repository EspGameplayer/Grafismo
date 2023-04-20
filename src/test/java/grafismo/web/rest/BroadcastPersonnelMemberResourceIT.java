package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.BroadcastPersonnelMember;
import grafismo.domain.Person;
import grafismo.domain.enumeration.BroadcastPersonnelMemberRole;
import grafismo.repository.BroadcastPersonnelMemberRepository;
import grafismo.service.BroadcastPersonnelMemberService;
import grafismo.service.dto.BroadcastPersonnelMemberDTO;
import grafismo.service.mapper.BroadcastPersonnelMemberMapper;
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
 * Integration tests for the {@link BroadcastPersonnelMemberResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BroadcastPersonnelMemberResourceIT {

    private static final BroadcastPersonnelMemberRole DEFAULT_ROLE = BroadcastPersonnelMemberRole.NARRATOR;
    private static final BroadcastPersonnelMemberRole UPDATED_ROLE = BroadcastPersonnelMemberRole.ANALYST;

    private static final String ENTITY_API_URL = "/api/broadcast-personnel-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepository;

    @Mock
    private BroadcastPersonnelMemberRepository broadcastPersonnelMemberRepositoryMock;

    @Autowired
    private BroadcastPersonnelMemberMapper broadcastPersonnelMemberMapper;

    @Mock
    private BroadcastPersonnelMemberService broadcastPersonnelMemberServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBroadcastPersonnelMemberMockMvc;

    private BroadcastPersonnelMember broadcastPersonnelMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BroadcastPersonnelMember createEntity(EntityManager em) {
        BroadcastPersonnelMember broadcastPersonnelMember = new BroadcastPersonnelMember().role(DEFAULT_ROLE);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        broadcastPersonnelMember.setPerson(person);
        return broadcastPersonnelMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BroadcastPersonnelMember createUpdatedEntity(EntityManager em) {
        BroadcastPersonnelMember broadcastPersonnelMember = new BroadcastPersonnelMember().role(UPDATED_ROLE);
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        broadcastPersonnelMember.setPerson(person);
        return broadcastPersonnelMember;
    }

    @BeforeEach
    public void initTest() {
        broadcastPersonnelMember = createEntity(em);
    }

    @Test
    @Transactional
    void createBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeCreate = broadcastPersonnelMemberRepository.findAll().size();
        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);
        restBroadcastPersonnelMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeCreate + 1);
        BroadcastPersonnelMember testBroadcastPersonnelMember = broadcastPersonnelMemberList.get(broadcastPersonnelMemberList.size() - 1);
        assertThat(testBroadcastPersonnelMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createBroadcastPersonnelMemberWithExistingId() throws Exception {
        // Create the BroadcastPersonnelMember with an existing ID
        broadcastPersonnelMember.setId(1L);
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        int databaseSizeBeforeCreate = broadcastPersonnelMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBroadcastPersonnelMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBroadcastPersonnelMembers() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        // Get all the broadcastPersonnelMemberList
        restBroadcastPersonnelMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(broadcastPersonnelMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBroadcastPersonnelMembersWithEagerRelationshipsIsEnabled() throws Exception {
        when(broadcastPersonnelMemberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBroadcastPersonnelMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(broadcastPersonnelMemberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBroadcastPersonnelMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(broadcastPersonnelMemberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBroadcastPersonnelMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(broadcastPersonnelMemberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBroadcastPersonnelMember() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        // Get the broadcastPersonnelMember
        restBroadcastPersonnelMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, broadcastPersonnelMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(broadcastPersonnelMember.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBroadcastPersonnelMember() throws Exception {
        // Get the broadcastPersonnelMember
        restBroadcastPersonnelMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBroadcastPersonnelMember() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();

        // Update the broadcastPersonnelMember
        BroadcastPersonnelMember updatedBroadcastPersonnelMember = broadcastPersonnelMemberRepository
            .findById(broadcastPersonnelMember.getId())
            .get();
        // Disconnect from session so that the updates on updatedBroadcastPersonnelMember are not directly saved in db
        em.detach(updatedBroadcastPersonnelMember);
        updatedBroadcastPersonnelMember.role(UPDATED_ROLE);
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(updatedBroadcastPersonnelMember);

        restBroadcastPersonnelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, broadcastPersonnelMemberDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
        BroadcastPersonnelMember testBroadcastPersonnelMember = broadcastPersonnelMemberList.get(broadcastPersonnelMemberList.size() - 1);
        assertThat(testBroadcastPersonnelMember.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, broadcastPersonnelMemberDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBroadcastPersonnelMemberWithPatch() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();

        // Update the broadcastPersonnelMember using partial update
        BroadcastPersonnelMember partialUpdatedBroadcastPersonnelMember = new BroadcastPersonnelMember();
        partialUpdatedBroadcastPersonnelMember.setId(broadcastPersonnelMember.getId());

        restBroadcastPersonnelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBroadcastPersonnelMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBroadcastPersonnelMember))
            )
            .andExpect(status().isOk());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
        BroadcastPersonnelMember testBroadcastPersonnelMember = broadcastPersonnelMemberList.get(broadcastPersonnelMemberList.size() - 1);
        assertThat(testBroadcastPersonnelMember.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateBroadcastPersonnelMemberWithPatch() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();

        // Update the broadcastPersonnelMember using partial update
        BroadcastPersonnelMember partialUpdatedBroadcastPersonnelMember = new BroadcastPersonnelMember();
        partialUpdatedBroadcastPersonnelMember.setId(broadcastPersonnelMember.getId());

        partialUpdatedBroadcastPersonnelMember.role(UPDATED_ROLE);

        restBroadcastPersonnelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBroadcastPersonnelMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBroadcastPersonnelMember))
            )
            .andExpect(status().isOk());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
        BroadcastPersonnelMember testBroadcastPersonnelMember = broadcastPersonnelMemberList.get(broadcastPersonnelMemberList.size() - 1);
        assertThat(testBroadcastPersonnelMember.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, broadcastPersonnelMemberDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBroadcastPersonnelMember() throws Exception {
        int databaseSizeBeforeUpdate = broadcastPersonnelMemberRepository.findAll().size();
        broadcastPersonnelMember.setId(count.incrementAndGet());

        // Create the BroadcastPersonnelMember
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO = broadcastPersonnelMemberMapper.toDto(broadcastPersonnelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBroadcastPersonnelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(broadcastPersonnelMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BroadcastPersonnelMember in the database
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBroadcastPersonnelMember() throws Exception {
        // Initialize the database
        broadcastPersonnelMemberRepository.saveAndFlush(broadcastPersonnelMember);

        int databaseSizeBeforeDelete = broadcastPersonnelMemberRepository.findAll().size();

        // Delete the broadcastPersonnelMember
        restBroadcastPersonnelMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, broadcastPersonnelMember.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BroadcastPersonnelMember> broadcastPersonnelMemberList = broadcastPersonnelMemberRepository.findAll();
        assertThat(broadcastPersonnelMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
