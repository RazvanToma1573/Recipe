package razvan.toma.recipe.Repository;

import org.springframework.data.repository.CrudRepository;
import razvan.toma.recipe.Domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
