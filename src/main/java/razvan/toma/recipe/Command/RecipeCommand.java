package razvan.toma.recipe.Command;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import razvan.toma.recipe.Domain.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredientSet = new HashSet<>();
    private Set<CategoryCommand> categorySet = new HashSet<>();
}
