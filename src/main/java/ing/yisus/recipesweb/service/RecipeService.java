package ing.yisus.recipesweb.service;

import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeEntity getRecipe(Long id){
        return recipeRepository.findById(id)
                .orElse(null);
    }

    public Long getRecipeCount() {
        return recipeRepository.count();
    }

    public void saveRecipe(RecipeEntity recipe) {
        recipeRepository.save(recipe);
    }

    public RecipeEntity getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));
    }
}
