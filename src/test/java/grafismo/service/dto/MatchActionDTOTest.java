package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchActionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchActionDTO.class);
        MatchActionDTO matchActionDTO1 = new MatchActionDTO();
        matchActionDTO1.setId(1L);
        MatchActionDTO matchActionDTO2 = new MatchActionDTO();
        assertThat(matchActionDTO1).isNotEqualTo(matchActionDTO2);
        matchActionDTO2.setId(matchActionDTO1.getId());
        assertThat(matchActionDTO1).isEqualTo(matchActionDTO2);
        matchActionDTO2.setId(2L);
        assertThat(matchActionDTO1).isNotEqualTo(matchActionDTO2);
        matchActionDTO1.setId(null);
        assertThat(matchActionDTO1).isNotEqualTo(matchActionDTO2);
    }
}
