package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphicElementMapperTest {

    private GraphicElementMapper graphicElementMapper;

    @BeforeEach
    public void setUp() {
        graphicElementMapper = new GraphicElementMapperImpl();
    }
}
