package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.RecipeDto;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.RecipeService;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.RecipeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/api/recipes/")

public class RestRecipeControl {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final UserService userService;

    @GetMapping("nextRecipeId")
    public ResponseEntity<Long> getNextRecipeId() {
        return ResponseEntity.ok(recipeService.getRecipeCount());
    }

    @GetMapping("all")
    public ResponseEntity<List<RecipeDto>> getAllRecipes(Pageable pageable) {
        List<RecipeDto> recipes = recipeService.getAllRecipes(pageable).stream()
                .map(recipeMapper::toDto).toList();
        System.out.println(recipes);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("fav-recipes/{userId}")
    public ResponseEntity<?> getFavsRecipes(@PathVariable Long userId, Pageable pageable) {
        UserEntity userEntity = userService.findUserById(userId);
        if(userEntity == null) {
            return ResponseEntity.badRequest().body("The user isn´t in the BD");
        }

        List<RecipeDto> favRecipes = recipeService.getFavsByUserId(userEntity, pageable)
                .stream().map(recipeMapper::toDto).toList();
        if (favRecipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favRecipes);
    }

    @GetMapping("user-recipes/{userId}")
    public ResponseEntity<?> getUserRecipes(@PathVariable Long userId, Pageable pageable) {
        UserEntity userEntity = userService.findUserById(userId);

        if(userEntity == null) {
            return ResponseEntity.badRequest().body("The user isn´t in the BD");
        }

        List<RecipeDto> userRecipes = recipeService.getRecipesByUserId(userEntity.getId(), pageable).stream()
                .map(recipeMapper::toDto).toList();
        //Check if the user has any recipes
        if (userRecipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userRecipes);
    }

    @PostMapping("update")
        public ResponseEntity<RecipeDto> update(@RequestBody RecipeDto recipeDto) {
        if (recipeDto.getId() == null || recipeDto.getId() < 0) {
            return ResponseEntity.badRequest().body(null);
        }
        RecipeDto updatedRecipe = recipeMapper.toDto(recipeService.saveRecipe(recipeMapper.toEntity(recipeDto, userService.findUserById(recipeDto.getUserId()))));
        return ResponseEntity.ok(updatedRecipe);
    }

    @GetMapping("{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        if (id < 0 || id >= recipeService.getRecipeCount()) {
            return ResponseEntity.notFound().build();
        }
        RecipeDto recipe = recipeMapper.toDto(recipeService.getRecipeById(id));
        return ResponseEntity.ok(recipe);
    }
}