package razvan.toma.recipe.Service;

import razvan.toma.recipe.Command.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipe, Long ingredient);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteIngredientFromRecipe(Long recipe, Long ingredient);
}
