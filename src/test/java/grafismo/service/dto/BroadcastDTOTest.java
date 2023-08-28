package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BroadcastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BroadcastDTO.class);
        BroadcastDTO broadcastDTO1 = new BroadcastDTO();
        broadcastDTO1.setId(1L);
        BroadcastDTO broadcastDTO2 = new BroadcastDTO();
        assertThat(broadcastDTO1).isNotEqualTo(broadcastDTO2);
        broadcastDTO2.setId(broadcastDTO1.getId());
        assertThat(broadcastDTO1).isEqualTo(broadcastDTO2);
        broadcastDTO2.setId(2L);
        assertThat(broadcastDTO1).isNotEqualTo(broadcastDTO2);
        broadcastDTO1.setId(null);
        assertThat(broadcastDTO1).isNotEqualTo(broadcastDTO2);
    }
}
