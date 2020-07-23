package razvan.toma.recipe.Repository;

import org.springframework.data.repository.CrudRepository;
import razvan.toma.recipe.Domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);

}
