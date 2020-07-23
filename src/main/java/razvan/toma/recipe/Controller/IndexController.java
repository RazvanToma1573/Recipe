package razvan.toma.recipe.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"/", "", "/index"})
    public String getIndexPage() {

        System.out.println("I got here!!! Everything is cool!!! No, you are cooler!!!");

        return "index";
    }
}
