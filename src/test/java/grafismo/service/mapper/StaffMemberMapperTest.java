package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StaffMemberMapperTest {

    private StaffMemberMapper staffMemberMapper;

    @BeforeEach
    public void setUp() {
        staffMemberMapper = new StaffMemberMapperImpl();
    }
}
