package razvan.toma.recipe.Service;

import razvan.toma.recipe.Command.RecipeCommand;
import razvan.toma.recipe.Domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findCommandById(Long id);
    void deleteById(Long id);
}
