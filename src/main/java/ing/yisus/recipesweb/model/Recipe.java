package ing.yisus.recipesweb.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Recipe {
    private int numOfLikes;
    private String title;
    private String[] ingredients;
    private String instructions;
    private ArrayList<FoodIntention> foodIntentions;
    private ArrayList<FoodType> foodTypes;
}