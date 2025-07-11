package ing.yisus.recipesweb.persistence;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    private Long id;
    @Column(name = "name")
    private String username;
    private String password;
    private String role;
    //Map favsRecipes
    @ManyToMany
    @JoinTable(
            name = "user_favorite_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<RecipeEntity> favs;
}
