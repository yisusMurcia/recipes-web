package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.model.FoodIntention;
import ing.yisus.recipesweb.model.FoodType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enums")
public class EnumController {
    @GetMapping("/food-intentions")
    public FoodIntention[] getFoodIntentions() {
        return FoodIntention.values();
    }

    @GetMapping("/food-types")
    public FoodType[] getFoodTypes() {
        return FoodType.values();
    }
}