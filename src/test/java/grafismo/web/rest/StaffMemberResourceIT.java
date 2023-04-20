package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Person;
import grafismo.domain.StaffMember;
import grafismo.domain.enumeration.StaffMemberRole;
import grafismo.repository.StaffMemberRepository;
import grafismo.service.StaffMemberService;
import grafismo.service.dto.StaffMemberDTO;
import grafismo.service.mapper.StaffMemberMapper;
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
 * Integration tests for the {@link StaffMemberResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StaffMemberResourceIT {

    private static final StaffMemberRole DEFAULT_DEFAULT_ROLE = StaffMemberRole.DT;
    private static final StaffMemberRole UPDATED_DEFAULT_ROLE = StaffMemberRole.DT2;

    private static final String DEFAULT_CONTRACT_UNTIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_UNTIL = "BBBBBBBBBB";

    private static final String DEFAULT_RETIREMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_RETIREMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/staff-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StaffMemberRepository staffMemberRepository;

    @Mock
    private StaffMemberRepository staffMemberRepositoryMock;

    @Autowired
    private StaffMemberMapper staffMemberMapper;

    @Mock
    private StaffMemberService staffMemberServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaffMemberMockMvc;

    private StaffMember staffMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaffMember createEntity(EntityManager em) {
        StaffMember staffMember = new StaffMember()
            .defaultRole(DEFAULT_DEFAULT_ROLE)
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
        staffMember.setPerson(person);
        return staffMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaffMember createUpdatedEntity(EntityManager em) {
        StaffMember staffMember = new StaffMember()
            .defaultRole(UPDATED_DEFAULT_ROLE)
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
        staffMember.setPerson(person);
        return staffMember;
    }

    @BeforeEach
    public void initTest() {
        staffMember = createEntity(em);
    }

    @Test
    @Transactional
    void createStaffMember() throws Exception {
        int databaseSizeBeforeCreate = staffMemberRepository.findAll().size();
        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);
        restStaffMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeCreate + 1);
        StaffMember testStaffMember = staffMemberList.get(staffMemberList.size() - 1);
        assertThat(testStaffMember.getDefaultRole()).isEqualTo(DEFAULT_DEFAULT_ROLE);
        assertThat(testStaffMember.getContractUntil()).isEqualTo(DEFAULT_CONTRACT_UNTIL);
        assertThat(testStaffMember.getRetirementDate()).isEqualTo(DEFAULT_RETIREMENT_DATE);
        assertThat(testStaffMember.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createStaffMemberWithExistingId() throws Exception {
        // Create the StaffMember with an existing ID
        staffMember.setId(1L);
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        int databaseSizeBeforeCreate = staffMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMemberMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStaffMembers() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        // Get all the staffMemberList
        restStaffMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staffMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultRole").value(hasItem(DEFAULT_DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].contractUntil").value(hasItem(DEFAULT_CONTRACT_UNTIL)))
            .andExpect(jsonPath("$.[*].retirementDate").value(hasItem(DEFAULT_RETIREMENT_DATE)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStaffMembersWithEagerRelationshipsIsEnabled() throws Exception {
        when(staffMemberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStaffMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(staffMemberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStaffMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(staffMemberServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStaffMemberMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(staffMemberServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getStaffMember() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        // Get the staffMember
        restStaffMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, staffMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staffMember.getId().intValue()))
            .andExpect(jsonPath("$.defaultRole").value(DEFAULT_DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.contractUntil").value(DEFAULT_CONTRACT_UNTIL))
            .andExpect(jsonPath("$.retirementDate").value(DEFAULT_RETIREMENT_DATE))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingStaffMember() throws Exception {
        // Get the staffMember
        restStaffMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStaffMember() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();

        // Update the staffMember
        StaffMember updatedStaffMember = staffMemberRepository.findById(staffMember.getId()).get();
        // Disconnect from session so that the updates on updatedStaffMember are not directly saved in db
        em.detach(updatedStaffMember);
        updatedStaffMember
            .defaultRole(UPDATED_DEFAULT_ROLE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(updatedStaffMember);

        restStaffMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staffMemberDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
        StaffMember testStaffMember = staffMemberList.get(staffMemberList.size() - 1);
        assertThat(testStaffMember.getDefaultRole()).isEqualTo(UPDATED_DEFAULT_ROLE);
        assertThat(testStaffMember.getContractUntil()).isEqualTo(UPDATED_CONTRACT_UNTIL);
        assertThat(testStaffMember.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testStaffMember.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, staffMemberDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStaffMemberWithPatch() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();

        // Update the staffMember using partial update
        StaffMember partialUpdatedStaffMember = new StaffMember();
        partialUpdatedStaffMember.setId(staffMember.getId());

        partialUpdatedStaffMember.defaultRole(UPDATED_DEFAULT_ROLE).retirementDate(UPDATED_RETIREMENT_DATE).miscData(UPDATED_MISC_DATA);

        restStaffMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaffMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaffMember))
            )
            .andExpect(status().isOk());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
        StaffMember testStaffMember = staffMemberList.get(staffMemberList.size() - 1);
        assertThat(testStaffMember.getDefaultRole()).isEqualTo(UPDATED_DEFAULT_ROLE);
        assertThat(testStaffMember.getContractUntil()).isEqualTo(DEFAULT_CONTRACT_UNTIL);
        assertThat(testStaffMember.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testStaffMember.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateStaffMemberWithPatch() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();

        // Update the staffMember using partial update
        StaffMember partialUpdatedStaffMember = new StaffMember();
        partialUpdatedStaffMember.setId(staffMember.getId());

        partialUpdatedStaffMember
            .defaultRole(UPDATED_DEFAULT_ROLE)
            .contractUntil(UPDATED_CONTRACT_UNTIL)
            .retirementDate(UPDATED_RETIREMENT_DATE)
            .miscData(UPDATED_MISC_DATA);

        restStaffMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStaffMember.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStaffMember))
            )
            .andExpect(status().isOk());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
        StaffMember testStaffMember = staffMemberList.get(staffMemberList.size() - 1);
        assertThat(testStaffMember.getDefaultRole()).isEqualTo(UPDATED_DEFAULT_ROLE);
        assertThat(testStaffMember.getContractUntil()).isEqualTo(UPDATED_CONTRACT_UNTIL);
        assertThat(testStaffMember.getRetirementDate()).isEqualTo(UPDATED_RETIREMENT_DATE);
        assertThat(testStaffMember.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, staffMemberDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStaffMember() throws Exception {
        int databaseSizeBeforeUpdate = staffMemberRepository.findAll().size();
        staffMember.setId(count.incrementAndGet());

        // Create the StaffMember
        StaffMemberDTO staffMemberDTO = staffMemberMapper.toDto(staffMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStaffMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(staffMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StaffMember in the database
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStaffMember() throws Exception {
        // Initialize the database
        staffMemberRepository.saveAndFlush(staffMember);

        int databaseSizeBeforeDelete = staffMemberRepository.findAll().size();

        // Delete the staffMember
        restStaffMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, staffMember.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaffMember> staffMemberList = staffMemberRepository.findAll();
        assertThat(staffMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
