package ing.yisus.recipesweb.model;

/**
 * Represents a user in the recipe web application.
 * Contains fields for username, password, and arrays of recipes.
 *
 * @author Jesús Murcia
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    private String username;
    private String password;
    private String userRol;
}
