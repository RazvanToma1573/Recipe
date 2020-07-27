package razvan.toma.recipe.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razvan.toma.recipe.Command.CategoryCommand;
import razvan.toma.recipe.Domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand = CategoryCommand.builder().id(1L).description("great category").build();

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(1L, category.getId());
        assertEquals("great category", category.getDescription());
    }
}