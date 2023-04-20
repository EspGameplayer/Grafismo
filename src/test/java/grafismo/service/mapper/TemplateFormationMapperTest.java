package grafismo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateFormationMapperTest {

    private TemplateFormationMapper templateFormationMapper;

    @BeforeEach
    public void setUp() {
        templateFormationMapper = new TemplateFormationMapperImpl();
    }
}
