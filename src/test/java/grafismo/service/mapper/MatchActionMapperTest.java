package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatchActionMapperTest {

    private MatchActionMapper matchActionMapper;

    @BeforeEach
    public void setUp() {
        matchActionMapper = new MatchActionMapperImpl();
    }
}
