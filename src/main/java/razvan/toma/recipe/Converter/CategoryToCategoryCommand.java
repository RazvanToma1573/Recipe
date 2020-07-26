package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.CategoryCommand;
import razvan.toma.recipe.Domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand convertedCategoryCommand = CategoryCommand.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();

        return convertedCategoryCommand;
    }
}
