package ing.yisus.recipesweb.util;

import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.model.UserRol;
import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DtoUserMapper {

    public static User DtoToModel(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
        //Admin password
        String ADMIN_PASSWORD = "020admin81";
        if(userDto.getUserRol().equals("admin") && userDto.getAdminPassword() != null && userDto.getAdminPassword().equals(ADMIN_PASSWORD)) {
            user.setUserRol(UserRol.ADMIN);
        } else{
            user.setUserRol(UserRol.USER);
        }

        return user;
    }

    public static UserEntity DtoToEntity(UserDto userDto, Long id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setRole(userDto.getUserRol().toUpperCase());
        userEntity.setId(id); // Assuming ID is auto-incremented based on count
        userEntity.setFavs(new ArrayList<RecipeEntity>());
        userEntity.setFavs(new ArrayList<RecipeEntity>());
        return userEntity;
    }
}
