package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateFormationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateFormation.class);
        TemplateFormation templateFormation1 = new TemplateFormation();
        templateFormation1.setId(1L);
        TemplateFormation templateFormation2 = new TemplateFormation();
        templateFormation2.setId(templateFormation1.getId());
        assertThat(templateFormation1).isEqualTo(templateFormation2);
        templateFormation2.setId(2L);
        assertThat(templateFormation1).isNotEqualTo(templateFormation2);
        templateFormation1.setId(null);
        assertThat(templateFormation1).isNotEqualTo(templateFormation2);
    }
}
