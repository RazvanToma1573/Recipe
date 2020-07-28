package razvan.toma.recipe.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razvan.toma.recipe.Command.RecipeCommand;
import razvan.toma.recipe.Converter.RecipeCommandToRecipe;
import razvan.toma.recipe.Converter.RecipeToRecipeCommand;
import razvan.toma.recipe.Domain.Recipe;
import razvan.toma.recipe.Exception.NotFoundException;
import razvan.toma.recipe.Repository.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw  new NotFoundException("Recipe Not Found. For ID value: " + id.toString());
        }

        return recipeOptional.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe convertedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(convertedRecipe);
        log.debug("saved a new recipe: " + savedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        log.debug("found recipe: " + optionalRecipe);
        return optionalRecipe.map(recipeToRecipeCommand::convert).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
