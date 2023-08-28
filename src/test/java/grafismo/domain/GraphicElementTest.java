package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GraphicElementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GraphicElement.class);
        GraphicElement graphicElement1 = new GraphicElement();
        graphicElement1.setId(1L);
        GraphicElement graphicElement2 = new GraphicElement();
        graphicElement2.setId(graphicElement1.getId());
        assertThat(graphicElement1).isEqualTo(graphicElement2);
        graphicElement2.setId(2L);
        assertThat(graphicElement1).isNotEqualTo(graphicElement2);
        graphicElement1.setId(null);
        assertThat(graphicElement1).isNotEqualTo(graphicElement2);
    }
}
