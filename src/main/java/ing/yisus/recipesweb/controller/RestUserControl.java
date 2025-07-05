package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.DtoUserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequiredArgsConstructor
public class RestUserControl {
    private final UserService userService;
    @Value("${app.admin.password}")
    private String adminPassword;

    @GetMapping("/user")//For validate if user is logged in, it returns the username, if is not logged, return null
    public Object getUser(HttpSession session) {
        return session.getAttribute("userName");
    }

    @GetMapping("/validateUser")
    public ResponseEntity<?> validateUser(@RequestParam UserDto userDto) {
        UserEntity userFound = userService.findUserByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        if(userFound != null){
            return ResponseEntity.ok(userFound);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        //Validate user, can´t exist some with same username
        if(userService.findUserByUsername(userDto.getUsername()) != null){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        //Validate admin password if user is admin
        if(userDto.getUserRol().equalsIgnoreCase("admin")) {
            if(userDto.getAdminPassword() == null || !userDto.getAdminPassword().equals(adminPassword)) {
                return ResponseEntity.badRequest().body("Invalid admin password");
            }
        }
        userService.registerUser(DtoUserMapper.DtoToEntity(userDto, userService.getUserCount()));

        return ResponseEntity.ok(userDto);

    }

    @GetMapping("/userId/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable String username) {
        UserEntity userEntity = userService.findUserByUsername(username);
        if (userEntity != null) {
            return ResponseEntity.ok(userEntity.getId());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
