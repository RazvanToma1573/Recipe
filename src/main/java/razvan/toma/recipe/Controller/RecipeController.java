package razvan.toma.recipe.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import razvan.toma.recipe.Command.RecipeCommand;
import razvan.toma.recipe.Exception.NotFoundException;
import razvan.toma.recipe.Service.RecipeService;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String getRecipe(@PathVariable String id, Model model) {
        log.debug("Showing id: " + id + " ------ debug message");

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        log.debug("Adding a new Recipe" + " ------ debug message");

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        log.debug("Updating recipe (id): " + id + " ------ debug message");

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id: " + id + " ------ debug message");

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        log.debug("Added or updated recipe" + " ------ debug message");

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound() {
        log.error("Handling not found exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        return modelAndView;
    }
}
