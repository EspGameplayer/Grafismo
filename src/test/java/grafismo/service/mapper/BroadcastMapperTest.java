package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BroadcastMapperTest {

    private BroadcastMapper broadcastMapper;

    @BeforeEach
    public void setUp() {
        broadcastMapper = new BroadcastMapperImpl();
    }
}
