package razvan.toma.recipe.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import razvan.toma.recipe.Service.RecipeService;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Show index page ------ debug message");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
