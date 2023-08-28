package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MatchActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MatchAction.class);
        MatchAction matchAction1 = new MatchAction();
        matchAction1.setId(1L);
        MatchAction matchAction2 = new MatchAction();
        matchAction2.setId(matchAction1.getId());
        assertThat(matchAction1).isEqualTo(matchAction2);
        matchAction2.setId(2L);
        assertThat(matchAction1).isNotEqualTo(matchAction2);
        matchAction1.setId(null);
        assertThat(matchAction1).isNotEqualTo(matchAction2);
    }
}
