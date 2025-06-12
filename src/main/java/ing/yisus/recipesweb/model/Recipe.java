package ing.yisus.recipesweb.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recipe {
    private int numOfLikes;
    private String title;
    private String description;
    private String[] ingredients;
    private String instructions;
    private FoodIntention[] foodIntentions;
    private FoodType[] foodTypes;
}