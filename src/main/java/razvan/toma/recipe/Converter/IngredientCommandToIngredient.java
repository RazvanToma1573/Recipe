package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Domain.Ingredient;
import razvan.toma.recipe.Domain.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = Ingredient.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .description(source.getDescription())
                .unitOfMeasure(unitOfMeasureCommandToUnitOfMeasure.convert(source.getUnitOfMeasure()))
                .build();

        if (source.getRecipeId() != null) {
            Recipe recipe = Recipe.builder().id(source.getRecipeId()).build();
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        return ingredient;
    }

}
