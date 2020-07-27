package razvan.toma.recipe.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razvan.toma.recipe.Command.UnitOfMeasureCommand;
import razvan.toma.recipe.Domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }


    @Test
    void convert() {
        //given
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(1L)
                .description("cool uom")
                .build();

        //when
        UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);

        //then
        assertNotNull(unitOfMeasureCommand);
        assertEquals(1L, unitOfMeasureCommand.getId());
        assertEquals("cool uom", unitOfMeasureCommand.getDescription());
    }
}