package ing.yisus.recipesweb.Dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {
    private Long id;
    private String title;
    private String ingredients;
    private String instructions;
    private List<String> foodIntentions;
    private List<String> foodTypes;
}
