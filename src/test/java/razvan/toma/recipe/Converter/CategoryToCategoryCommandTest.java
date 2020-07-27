package razvan.toma.recipe.Converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import razvan.toma.recipe.Command.CategoryCommand;
import razvan.toma.recipe.Domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = Category.builder().id(1L).description("great category").build();

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertEquals(1L, categoryCommand.getId());
        assertEquals("great category", categoryCommand.getDescription());
    }
}