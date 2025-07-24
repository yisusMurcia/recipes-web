package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.LoginDto;
import ing.yisus.recipesweb.Dto.RegisterDto;
import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.persistence.RecipeEntity;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.RecipeService;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/user/")
public class RestUserControl {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RecipeService recipeService;
    @Value("${app.admin.password}")
    private String adminPassword;


    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody RegisterDto registerDto) {
        //Validate user, it can't exist some with the same username
        if(userService.findUserByUsername(registerDto.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        //Validate admin password if the user is admin
        if(registerDto.getUserRol().equalsIgnoreCase("admin")) {
            if(registerDto.getAdminPassword() == null || !registerDto.getAdminPassword().equals(adminPassword)) {
                return ResponseEntity.badRequest().body("Invalid admin password");
            }
        }
        userService.registerUser(userMapper.registerDtoToEntity(registerDto));

        return ResponseEntity.ok(registerDto);

    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto user){
        UserEntity userFound = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(userFound != null) {
            UserDto userDto = userMapper.entityToDto(userFound);
            return ResponseEntity.ok(userDto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{userId}/addFav/{recipeId}")
    public ResponseEntity<?> addFav(@PathVariable Long userId, @PathVariable Long recipeId) {
        UserEntity userEntity = userService.findUserById(userId);
        if(userEntity == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        RecipeEntity recipeEntity = recipeService.getRecipeById(recipeId);
        if(recipeEntity == null) {
            return ResponseEntity.badRequest().body("Recipe not found");
        }

        userService.addToFavs(recipeEntity, userEntity);

        return ResponseEntity.ok(recipeEntity);
    }
}
