package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BroadcastPersonnelMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BroadcastPersonnelMember.class);
        BroadcastPersonnelMember broadcastPersonnelMember1 = new BroadcastPersonnelMember();
        broadcastPersonnelMember1.setId(1L);
        BroadcastPersonnelMember broadcastPersonnelMember2 = new BroadcastPersonnelMember();
        broadcastPersonnelMember2.setId(broadcastPersonnelMember1.getId());
        assertThat(broadcastPersonnelMember1).isEqualTo(broadcastPersonnelMember2);
        broadcastPersonnelMember2.setId(2L);
        assertThat(broadcastPersonnelMember1).isNotEqualTo(broadcastPersonnelMember2);
        broadcastPersonnelMember1.setId(null);
        assertThat(broadcastPersonnelMember1).isNotEqualTo(broadcastPersonnelMember2);
    }
}
