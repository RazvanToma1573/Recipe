package razvan.toma.recipe.Repository;

import org.springframework.data.repository.CrudRepository;
import razvan.toma.recipe.Domain.Category;

public interface CategoryRecipe extends CrudRepository<Category, Long> {
}
