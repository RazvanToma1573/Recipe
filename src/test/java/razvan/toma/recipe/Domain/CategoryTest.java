package razvan.toma.recipe.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
        String descriptionValue = "test description";
        category.setDescription(descriptionValue);
        assertEquals(descriptionValue, category.getDescription());
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        category.setRecipes(recipes);
        assertEquals(recipes.size(), category.getRecipes().size());
    }
}