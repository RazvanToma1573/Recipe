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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotes;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        Set<Category> convertedCategories = new HashSet<>();
        Set<Ingredient> convertedIngredients = new HashSet<>();

        Set<CategoryCommand> categoryCommands = source.getCategorySet();
        Set<IngredientCommand> ingredientCommands = source.getIngredientSet();

        if (categoryCommands != null && categoryCommands.size() > 1) {
            categoryCommands.forEach(categoryCommand -> {
                convertedCategories.add(categoryCommandToCategory.convert(categoryCommand));
            });
        }

        if (ingredientCommands != null && ingredientCommands.size() > 1) {
            ingredientCommands.forEach(ingredientCommand -> {
                convertedIngredients.add(ingredientCommandToIngredient.convert(ingredientCommand));
            });
        }

        final Recipe recipe = Recipe.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .difficulty(source.getDifficulty())
                .source(source.getSource())
                .url(source.getUrl())
                .directions(source.getDirections())
                .notes(notesCommandToNotes.convert(source.getNotes()))
                .categorySet(convertedCategories)
                .ingredientSet(convertedIngredients)
                .build();

        return recipe;
    }
}
