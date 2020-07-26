package razvan.toma.recipe.Converter;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import razvan.toma.recipe.Command.CategoryCommand;
import razvan.toma.recipe.Domain.Category;


@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null){
            return null;
        }


        final Category convertedCategory = Category.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();

        return convertedCategory;
    }
}
