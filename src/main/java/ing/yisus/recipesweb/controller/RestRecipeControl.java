package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RestRecipeControl {
    private final RecipeService recipeService;

    @GetMapping("/nextRecipeId")
    public Long getNextRecipeId() {
        return recipeService.getRecipeCount();
    }
}
