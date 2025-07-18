package ing.yisus.recipesweb.util;

import ing.yisus.recipesweb.Dto.LoginDto;
import ing.yisus.recipesweb.Dto.RegisterDto;
import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserService userService;

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

    public static User entityToModel(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .userRol(userEntity.getRole())
                .build();
    }

    public UserDto entityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .userRol(userEntity.getRole())
                .build();
    }

    public UserEntity userLoginToEntity(LoginDto user) {
        return userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    public UserEntity registerDtoToEntity(RegisterDto registerDto) {
        return UserEntity.builder()
                .id(userService.getUserCount())
                .username(registerDto.getUsername())
                .password(registerDto.getPassword())
                .role(registerDto.getUserRol().toUpperCase())
                .favs(new ArrayList<>())
                .build();
    }
}
