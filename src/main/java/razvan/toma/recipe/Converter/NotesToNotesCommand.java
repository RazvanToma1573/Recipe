package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.NotesCommand;
import razvan.toma.recipe.Domain.Notes;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = NotesCommand.builder()
                .id(source.getId())
                .recipeNotes(source.getRecipeNotes())
                .build();

        return notesCommand;
    }
}
