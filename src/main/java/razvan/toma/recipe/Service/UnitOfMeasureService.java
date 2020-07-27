package razvan.toma.recipe.Service;

import razvan.toma.recipe.Command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();

}
