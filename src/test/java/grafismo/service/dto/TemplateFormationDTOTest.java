package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateFormationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFormationDTO.class);
        TemplateFormationDTO templateFormationDTO1 = new TemplateFormationDTO();
        templateFormationDTO1.setId(1L);
        TemplateFormationDTO templateFormationDTO2 = new TemplateFormationDTO();
        assertThat(templateFormationDTO1).isNotEqualTo(templateFormationDTO2);
        templateFormationDTO2.setId(templateFormationDTO1.getId());
        assertThat(templateFormationDTO1).isEqualTo(templateFormationDTO2);
        templateFormationDTO2.setId(2L);
        assertThat(templateFormationDTO1).isNotEqualTo(templateFormationDTO2);
        templateFormationDTO1.setId(null);
        assertThat(templateFormationDTO1).isNotEqualTo(templateFormationDTO2);
    }
}
