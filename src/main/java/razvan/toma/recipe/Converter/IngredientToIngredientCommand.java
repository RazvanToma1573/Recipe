package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Domain.Ingredient;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = IngredientCommand.builder()
                .id(source.getId())
                .recipeId(source.getRecipe().getId())
                .amount(source.getAmount())
                .description(source.getDescription())
                .unitOfMeasure(unitOfMeasureToUnitOfMeasureCommand.convert(source.getUnitOfMeasure()))
                .build();

        return ingredientCommand;
    }
}

