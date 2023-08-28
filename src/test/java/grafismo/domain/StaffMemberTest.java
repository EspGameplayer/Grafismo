package grafismo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import grafismo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StaffMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaffMember.class);
        StaffMember staffMember1 = new StaffMember();
        staffMember1.setId(1L);
        StaffMember staffMember2 = new StaffMember();
        staffMember2.setId(staffMember1.getId());
        assertThat(staffMember1).isEqualTo(staffMember2);
        staffMember2.setId(2L);
        assertThat(staffMember1).isNotEqualTo(staffMember2);
        staffMember1.setId(null);
        assertThat(staffMember1).isNotEqualTo(staffMember2);
    }
}
