package razvan.toma.recipe.Repository;

import org.springframework.data.repository.CrudRepository;
import razvan.toma.recipe.Domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
