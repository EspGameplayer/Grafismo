package grafismo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import grafismo.IntegrationTest;
import grafismo.domain.Sponsor;
import grafismo.repository.SponsorRepository;
import grafismo.service.dto.SponsorDTO;
import grafismo.service.mapper.SponsorMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link SponsorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SponsorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRAPHICS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GRAPHICS_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MONOC_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MONOC_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MONOC_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MONOC_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MISC_DATA = "AAAAAAAAAA";
    private static final String UPDATED_MISC_DATA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sponsors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private SponsorMapper sponsorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSponsorMockMvc;

    private Sponsor sponsor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sponsor createEntity(EntityManager em) {
        Sponsor sponsor = new Sponsor()
            .name(DEFAULT_NAME)
            .graphicsName(DEFAULT_GRAPHICS_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .monocLogo(DEFAULT_MONOC_LOGO)
            .monocLogoContentType(DEFAULT_MONOC_LOGO_CONTENT_TYPE)
            .details(DEFAULT_DETAILS)
            .miscData(DEFAULT_MISC_DATA);
        return sponsor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sponsor createUpdatedEntity(EntityManager em) {
        Sponsor sponsor = new Sponsor()
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .monocLogo(UPDATED_MONOC_LOGO)
            .monocLogoContentType(UPDATED_MONOC_LOGO_CONTENT_TYPE)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        return sponsor;
    }

    @BeforeEach
    public void initTest() {
        sponsor = createEntity(em);
    }

    @Test
    @Transactional
    void createSponsor() throws Exception {
        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();
        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);
        restSponsorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate + 1);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSponsor.getGraphicsName()).isEqualTo(DEFAULT_GRAPHICS_NAME);
        assertThat(testSponsor.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSponsor.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getMonocLogo()).isEqualTo(DEFAULT_MONOC_LOGO);
        assertThat(testSponsor.getMonocLogoContentType()).isEqualTo(DEFAULT_MONOC_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testSponsor.getMiscData()).isEqualTo(DEFAULT_MISC_DATA);
    }

    @Test
    @Transactional
    void createSponsorWithExistingId() throws Exception {
        // Create the Sponsor with an existing ID
        sponsor.setId(1L);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        int databaseSizeBeforeCreate = sponsorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSponsorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = sponsorRepository.findAll().size();
        // set the field null
        sponsor.setName(null);

        // Create the Sponsor, which fails.
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        restSponsorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSponsors() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get all the sponsorList
        restSponsorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sponsor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].graphicsName").value(hasItem(DEFAULT_GRAPHICS_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].monocLogoContentType").value(hasItem(DEFAULT_MONOC_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].monocLogo").value(hasItem(Base64Utils.encodeToString(DEFAULT_MONOC_LOGO))))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].miscData").value(hasItem(DEFAULT_MISC_DATA)));
    }

    @Test
    @Transactional
    void getSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        // Get the sponsor
        restSponsorMockMvc
            .perform(get(ENTITY_API_URL_ID, sponsor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sponsor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.graphicsName").value(DEFAULT_GRAPHICS_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.monocLogoContentType").value(DEFAULT_MONOC_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.monocLogo").value(Base64Utils.encodeToString(DEFAULT_MONOC_LOGO)))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.miscData").value(DEFAULT_MISC_DATA));
    }

    @Test
    @Transactional
    void getNonExistingSponsor() throws Exception {
        // Get the sponsor
        restSponsorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor
        Sponsor updatedSponsor = sponsorRepository.findById(sponsor.getId()).get();
        // Disconnect from session so that the updates on updatedSponsor are not directly saved in db
        em.detach(updatedSponsor);
        updatedSponsor
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .monocLogo(UPDATED_MONOC_LOGO)
            .monocLogoContentType(UPDATED_MONOC_LOGO_CONTENT_TYPE)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);
        SponsorDTO sponsorDTO = sponsorMapper.toDto(updatedSponsor);

        restSponsorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sponsorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSponsor.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testSponsor.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSponsor.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getMonocLogo()).isEqualTo(UPDATED_MONOC_LOGO);
        assertThat(testSponsor.getMonocLogoContentType()).isEqualTo(UPDATED_MONOC_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testSponsor.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void putNonExistingSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sponsorDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSponsorWithPatch() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor using partial update
        Sponsor partialUpdatedSponsor = new Sponsor();
        partialUpdatedSponsor.setId(sponsor.getId());

        partialUpdatedSponsor.graphicsName(UPDATED_GRAPHICS_NAME).miscData(UPDATED_MISC_DATA);

        restSponsorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSponsor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSponsor))
            )
            .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSponsor.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testSponsor.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSponsor.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getMonocLogo()).isEqualTo(DEFAULT_MONOC_LOGO);
        assertThat(testSponsor.getMonocLogoContentType()).isEqualTo(DEFAULT_MONOC_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testSponsor.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void fullUpdateSponsorWithPatch() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();

        // Update the sponsor using partial update
        Sponsor partialUpdatedSponsor = new Sponsor();
        partialUpdatedSponsor.setId(sponsor.getId());

        partialUpdatedSponsor
            .name(UPDATED_NAME)
            .graphicsName(UPDATED_GRAPHICS_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .monocLogo(UPDATED_MONOC_LOGO)
            .monocLogoContentType(UPDATED_MONOC_LOGO_CONTENT_TYPE)
            .details(UPDATED_DETAILS)
            .miscData(UPDATED_MISC_DATA);

        restSponsorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSponsor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSponsor))
            )
            .andExpect(status().isOk());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
        Sponsor testSponsor = sponsorList.get(sponsorList.size() - 1);
        assertThat(testSponsor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSponsor.getGraphicsName()).isEqualTo(UPDATED_GRAPHICS_NAME);
        assertThat(testSponsor.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSponsor.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getMonocLogo()).isEqualTo(UPDATED_MONOC_LOGO);
        assertThat(testSponsor.getMonocLogoContentType()).isEqualTo(UPDATED_MONOC_LOGO_CONTENT_TYPE);
        assertThat(testSponsor.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testSponsor.getMiscData()).isEqualTo(UPDATED_MISC_DATA);
    }

    @Test
    @Transactional
    void patchNonExistingSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sponsorDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSponsor() throws Exception {
        int databaseSizeBeforeUpdate = sponsorRepository.findAll().size();
        sponsor.setId(count.incrementAndGet());

        // Create the Sponsor
        SponsorDTO sponsorDTO = sponsorMapper.toDto(sponsor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSponsorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sponsorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sponsor in the database
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSponsor() throws Exception {
        // Initialize the database
        sponsorRepository.saveAndFlush(sponsor);

        int databaseSizeBeforeDelete = sponsorRepository.findAll().size();

        // Delete the sponsor
        restSponsorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sponsor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sponsor> sponsorList = sponsorRepository.findAll();
        assertThat(sponsorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
