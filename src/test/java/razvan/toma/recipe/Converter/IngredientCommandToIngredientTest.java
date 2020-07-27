package razvan.toma.recipe.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Command.UnitOfMeasureCommand;
import razvan.toma.recipe.Domain.Ingredient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient converter;
    UnitOfMeasureCommandToUnitOfMeasure converter2;

    @BeforeEach
    void setUp() {
        converter2 = new UnitOfMeasureCommandToUnitOfMeasure();
        converter = new IngredientCommandToIngredient(converter2);
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = UnitOfMeasureCommand.builder()
                .id(1L)
                .description("great unit of measure")
                .build();
        IngredientCommand ingredientCommand = IngredientCommand.builder()
                .id(1L)
                .description("great description")
                .amount(new BigDecimal(1))
                .uom(unitOfMeasureCommand)
                .build();

        //when
        Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(1L, ingredient.getId());
        assertEquals("great description", ingredient.getDescription());
        assertEquals(new BigDecimal(1), ingredient.getAmount());
        assertEquals(1L, ingredient.getUom().getId());
        assertEquals("great unit of measure", ingredient.getUom().getDescription());
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setAmount(new BigDecimal(1));
        command.setDescription("great description");
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();


        //when
        Ingredient ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(1L, ingredient.getId());
        assertEquals(new BigDecimal(1), ingredient.getAmount());
        assertEquals("great description", ingredient.getDescription());
    }
}