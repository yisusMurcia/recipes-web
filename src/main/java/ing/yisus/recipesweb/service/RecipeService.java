package ing.yisus.recipesweb.service;

import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public RecipeEntity saveRecipe(RecipeEntity recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public RecipeEntity getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));
    }

    public Page<RecipeEntity> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Page<RecipeEntity> getRecipesByUserId(Long userId, Pageable pageable) {
        return recipeRepository.findAllByUserId(userId, pageable);
    }

    public Page<RecipeEntity> getFavsByUserId(UserEntity userEntity, Pageable pageable) {
        return recipeRepository.findFavsByUserId(userEntity, pageable);
    }

    public boolean hasFavorite(Long recipeId, Long userId){
        return recipeRepository.existsByRecipeIdAndUserId(recipeId, userId);
    }
}
