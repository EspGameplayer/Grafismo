package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GraphicElementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GraphicElementDTO.class);
        GraphicElementDTO graphicElementDTO1 = new GraphicElementDTO();
        graphicElementDTO1.setId(1L);
        GraphicElementDTO graphicElementDTO2 = new GraphicElementDTO();
        assertThat(graphicElementDTO1).isNotEqualTo(graphicElementDTO2);
        graphicElementDTO2.setId(graphicElementDTO1.getId());
        assertThat(graphicElementDTO1).isEqualTo(graphicElementDTO2);
        graphicElementDTO2.setId(2L);
        assertThat(graphicElementDTO1).isNotEqualTo(graphicElementDTO2);
        graphicElementDTO1.setId(null);
        assertThat(graphicElementDTO1).isNotEqualTo(graphicElementDTO2);
    }
}
