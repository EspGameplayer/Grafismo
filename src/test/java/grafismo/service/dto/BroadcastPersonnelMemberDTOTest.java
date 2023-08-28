package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BroadcastPersonnelMemberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BroadcastPersonnelMemberDTO.class);
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO1 = new BroadcastPersonnelMemberDTO();
        broadcastPersonnelMemberDTO1.setId(1L);
        BroadcastPersonnelMemberDTO broadcastPersonnelMemberDTO2 = new BroadcastPersonnelMemberDTO();
        assertThat(broadcastPersonnelMemberDTO1).isNotEqualTo(broadcastPersonnelMemberDTO2);
        broadcastPersonnelMemberDTO2.setId(broadcastPersonnelMemberDTO1.getId());
        assertThat(broadcastPersonnelMemberDTO1).isEqualTo(broadcastPersonnelMemberDTO2);
        broadcastPersonnelMemberDTO2.setId(2L);
        assertThat(broadcastPersonnelMemberDTO1).isNotEqualTo(broadcastPersonnelMemberDTO2);
        broadcastPersonnelMemberDTO1.setId(null);
        assertThat(broadcastPersonnelMemberDTO1).isNotEqualTo(broadcastPersonnelMemberDTO2);
    }
}
