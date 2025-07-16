package ing.yisus.recipesweb.Dto;

import lombok.Data;

@Data
public class RegisterDto {
    public String username;
    public String password;
    public String userRol;
    public String adminPassword;
}
