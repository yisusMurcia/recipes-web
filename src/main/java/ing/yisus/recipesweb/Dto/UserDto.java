package ing.yisus.recipesweb.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String userRol;
    private String adminPassword;//Password for validating admin permissions
}
