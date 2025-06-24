package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.DtoUserMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestControl {
    private final UserService userService;

    @PostMapping("register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String userRol, @RequestParam(required = false) String adminPassword, Model model) {
        UserDto userDto = new UserDto(username, password, userRol, null, null, adminPassword);
        User user = DtoUserMapper.DtoToModel(userDto);
        model.addAttribute("user", user);
        UserEntity entity = DtoUserMapper.DtoToEntity(userDto, userService.getUserCount() + 1);
        if(userService.registerUser(entity)){//Check if user is new
            return "";
        }else{
            return "register";
        }
    }
}
