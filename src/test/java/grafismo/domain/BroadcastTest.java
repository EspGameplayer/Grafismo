package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BroadcastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Broadcast.class);
        Broadcast broadcast1 = new Broadcast();
        broadcast1.setId(1L);
        Broadcast broadcast2 = new Broadcast();
        broadcast2.setId(broadcast1.getId());
        assertThat(broadcast1).isEqualTo(broadcast2);
        broadcast2.setId(2L);
        assertThat(broadcast1).isNotEqualTo(broadcast2);
        broadcast1.setId(null);
        assertThat(broadcast1).isNotEqualTo(broadcast2);
    }
}
