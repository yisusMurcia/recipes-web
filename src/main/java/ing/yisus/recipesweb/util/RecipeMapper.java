package ing.yisus.recipesweb.util;

import ing.yisus.recipesweb.Dto.RecipeDto;
import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeMapper {
    public RecipeEntity toEntity(RecipeDto recipeDto) {
        return RecipeEntity.builder()
                .id(recipeDto.getId())
                .title(recipeDto.getTitle())
                .ingredients(recipeDto.getIngredients())
                .instructions(recipeDto.getInstructions())
                .foodIntentions(String.join(",", recipeDto.getFoodIntentions()).toLowerCase())
                .foodTypes(String.join(",", recipeDto.getFoodTypes()).toLowerCase())
                .build();
    }

    public RecipeDto toDto(RecipeEntity recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .ingredients(recipe.getIngredients())
                .instructions(recipe.getInstructions())
                .foodIntentions(List.of(recipe.getFoodIntentions().split(",")))
                .foodTypes(List.of(recipe.getFoodTypes().split(",")))
                .build();
    }
}
