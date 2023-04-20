package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BroadcastPersonnelMemberMapperTest {

    private BroadcastPersonnelMemberMapper broadcastPersonnelMemberMapper;

    @BeforeEach
    public void setUp() {
        broadcastPersonnelMemberMapper = new BroadcastPersonnelMemberMapperImpl();
    }
}
