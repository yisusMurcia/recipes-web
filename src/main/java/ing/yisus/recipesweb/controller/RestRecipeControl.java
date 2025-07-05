package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RestRecipeControl {
    private final RecipeService recipeService;

    @GetMapping("/nextRecipeId")
    public ResponseEntity<Long> getNextRecipeId() {
        return ResponseEntity.ok(recipeService.getRecipeCount());
    }
}