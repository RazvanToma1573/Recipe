package razvan.toma.recipe.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import razvan.toma.recipe.Command.UnitOfMeasureCommand;
import razvan.toma.recipe.Converter.UnitOfMeasureToUnitOfMeasureCommand;
import razvan.toma.recipe.Domain.UnitOfMeasure;
import razvan.toma.recipe.Repository.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository repository;

    UnitOfMeasureToUnitOfMeasureCommand command;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        command = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(repository, command);
    }

    @Test
    void listAllUoms() {
        //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(repository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        //then
        assertEquals(2, commands.size());
        verify(repository, times(1)).findAll();
    }
}