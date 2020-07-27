package razvan.toma.recipe.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Domain.Ingredient;
import razvan.toma.recipe.Domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand converter;
    UnitOfMeasureToUnitOfMeasureCommand converter2;

    @BeforeEach
    void setUp() {
        converter2 = new UnitOfMeasureToUnitOfMeasureCommand();
        converter = new IngredientToIngredientCommand(converter2);
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(1L)
                .description("great unit of measure")
                .build();
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .description("great description")
                .amount(new BigDecimal(1))
                .uom(unitOfMeasure)
                .build();

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUom());
        assertEquals(1L, ingredientCommand.getId());
        assertEquals("great description", ingredientCommand.getDescription());
        assertEquals(new BigDecimal(1), ingredientCommand.getAmount());
        assertEquals(1L, ingredientCommand.getUom().getId());
        assertEquals("great unit of measure", ingredientCommand.getUom().getDescription());
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        //given
        Ingredient command = new Ingredient();
        command.setId(1L);
        command.setAmount(new BigDecimal(1));
        command.setDescription("great description");
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();


        //when
        IngredientCommand ingredientCommand = converter.convert(command);

        //then
        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUom());
        assertEquals(1L, ingredientCommand.getId());
        assertEquals(new BigDecimal(1), ingredientCommand.getAmount());
        assertEquals("great description", ingredientCommand.getDescription());
    }
}