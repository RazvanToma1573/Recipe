package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.CategoryCommand;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Command.RecipeCommand;
import razvan.toma.recipe.Domain.Category;
import razvan.toma.recipe.Domain.Ingredient;
import razvan.toma.recipe.Domain.Recipe;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        Set<CategoryCommand> convertedCategories = new HashSet<>();
        Set<IngredientCommand> convertedIngredients = new HashSet<>();

        Set<Category> categories = source.getCategorySet();
        Set<Ingredient> ingredients = source.getIngredientSet();

        if (categories != null && categories.size() > 1) {
            categories.forEach(category -> {
                convertedCategories.add(categoryToCategoryCommand.convert(category));
            });
        }

        if (ingredients != null && ingredients.size() > 1) {
            ingredients.forEach(ingredient -> {
                convertedIngredients.add(ingredientToIngredientCommand.convert(ingredient));
            });
        }

        final RecipeCommand recipeCommand = RecipeCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .difficulty(source.getDifficulty())
                .source(source.getSource())
                .url(source.getUrl())
                .directions(source.getDirections())
                .notes(notesToNotesCommand.convert(source.getNotes()))
                .categorySet(convertedCategories)
                .ingredientSet(convertedIngredients)
                .build();

        return recipeCommand;
    }
    
}
