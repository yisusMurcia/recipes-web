package ing.yisus.recipesweb.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "recipes")
public class RecipeEntity implements Serializable {
    @Id
    private Long id;
    private String title;
    private String instructions;
    @Column(name = "num_of_favs")
    private Long nunOfFavs;
    private String ingredients;
    @Column(name = "food_types")
    private String foodTypes;
    @Column(name = "food_intentions")
    private String foodIntentions;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
