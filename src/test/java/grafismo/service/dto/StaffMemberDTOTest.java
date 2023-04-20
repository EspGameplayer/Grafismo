package grafismo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StaffMemberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffMemberDTO.class);
        StaffMemberDTO staffMemberDTO1 = new StaffMemberDTO();
        staffMemberDTO1.setId(1L);
        StaffMemberDTO staffMemberDTO2 = new StaffMemberDTO();
        assertThat(staffMemberDTO1).isNotEqualTo(staffMemberDTO2);
        staffMemberDTO2.setId(staffMemberDTO1.getId());
        assertThat(staffMemberDTO1).isEqualTo(staffMemberDTO2);
        staffMemberDTO2.setId(2L);
        assertThat(staffMemberDTO1).isNotEqualTo(staffMemberDTO2);
        staffMemberDTO1.setId(null);
        assertThat(staffMemberDTO1).isNotEqualTo(staffMemberDTO2);
    }
}
