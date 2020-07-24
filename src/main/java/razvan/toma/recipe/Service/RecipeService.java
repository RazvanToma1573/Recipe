package razvan.toma.recipe.Service;

import razvan.toma.recipe.Domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
