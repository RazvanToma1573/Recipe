package razvan.toma.recipe.Repository;

import org.springframework.data.repository.CrudRepository;
import razvan.toma.recipe.Domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
