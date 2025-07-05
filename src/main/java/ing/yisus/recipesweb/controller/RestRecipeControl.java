package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.RecipeDto;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.RecipeService;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/")

public class RestRecipeControl {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final UserService userService;

    @GetMapping("nextRecipeId")
    public ResponseEntity<Long> getNextRecipeId() {
        return ResponseEntity.ok(recipeService.getRecipeCount());
    }

    @GetMapping("recipes")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes().stream()
                .map(recipeMapper::toDto).toList();
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("favs-recipes/{userId}")
    public ResponseEntity<List<RecipeDto>> getFavsRecipes(@PathVariable Long userId) {
        UserEntity user= userService.findUserById(userId);

        List<RecipeDto> favsRecipes = user.getFavs().stream()
                .map(recipeMapper::toDto).toList();
        //Check if the user has any favorite recipes
        if (favsRecipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favsRecipes);
    }

    @GetMapping("user-recipes/{userId}")
    public ResponseEntity<List<RecipeDto>> getUserRecipes(@PathVariable Long userId) {
        List<RecipeDto> userRecipes = recipeService.getRecipesByUserId(userId).stream()
                .map(recipeMapper::toDto).toList();
        //Check if the user has any recipes
        if (userRecipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userRecipes);
    }
}