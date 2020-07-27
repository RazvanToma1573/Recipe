package razvan.toma.recipe.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razvan.toma.recipe.Command.IngredientCommand;
import razvan.toma.recipe.Converter.IngredientCommandToIngredient;
import razvan.toma.recipe.Converter.IngredientToIngredientCommand;
import razvan.toma.recipe.Domain.Ingredient;
import razvan.toma.recipe.Domain.Recipe;
import razvan.toma.recipe.Repository.RecipeRepository;
import razvan.toma.recipe.Repository.UnitOfMeasureRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipe, Long ingredient) {
        log.debug("searching for ingredient with recipe id: " + recipe + " and ingredient id: " + ingredient + " ----- debug message");

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipe);

        if (optionalRecipe.isPresent()) {

            Recipe foundRecipe = optionalRecipe.get();

            if (foundRecipe.getIngredients() != null) {
                Optional<Ingredient> foundIngredient = foundRecipe.getIngredients()
                        .stream()
                        .filter(ingredient1 -> ingredient1.getId().equals(ingredient))
                        .findFirst();

                if (foundIngredient.isPresent()) {
                    Ingredient ingredient1 = foundIngredient.get();
                    IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient1);
                    return ingredientCommand;
                }
            }

        }

        return null;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {

            // todo: Toss error if not found
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUom(StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .filter(unitOfMeasure -> unitOfMeasure.getId().equals(ingredientCommand.getUom().getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("No unit of measure found"))); // todo: Address this
            } else {
                // add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }

    @Override
    @Transactional
    public void deleteIngredientFromRecipe(Long recipe, Long ingredient) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe);

        if (!recipeOptional.isPresent()) {
            // todo: raise error recipe not found
            log.error("Recipe not found");
        } else {
            Recipe recipe1 = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe1.getIngredients()
                    .stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredient))
                    .findFirst();

            if (!ingredientOptional.isPresent()) {
                // todo: raise error ingredient not found
                log.error("Ingredient not found");
            } else {
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe1.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe1);
            }
        }


    }
}
