package razvan.toma.recipe.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.NotesCommand;
import razvan.toma.recipe.Domain.Notes;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }

        final Notes notes = Notes.builder()
                .id(source.getId())
                .recipeNotes(source.getRecipeNotes())
                .build();

        return notes;
    }
}
