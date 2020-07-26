package razvan.toma.recipe.Command;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
}
