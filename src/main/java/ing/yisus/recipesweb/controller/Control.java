package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.DtoUserMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class Control {
    private final UserService userService;

    @GetMapping("sign-up")
    public String signUp(Model model) {
        return "sign-up";
    }
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String userRol, @RequestParam(required = false) String adminPassword, Model model, HttpSession session) {
        UserDto userDto = new UserDto(username, password, userRol, null, null, adminPassword);
        User user = DtoUserMapper.DtoToModel(userDto);
        UserEntity entity = DtoUserMapper.DtoToEntity(userDto, userService.getUserCount());
        if(userService.registerUser(entity)){//Check if user is new
            session.setAttribute("user", user);
            model.addAttribute("username", user.getUsername());
            return "index";
        }else{
            model.addAttribute("errorMessage", "Nombre de usuario en uso");
            return "sign-up";
        }
    }

    @GetMapping("loginUser")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            model.addAttribute("username", user.getUsername());
            session.setAttribute("userName", user.getUsername());
            return "index";
        } else {
            model.addAttribute("errorMessage", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("recipe/{id}")
    public String recipe(@PathVariable long id) {

        return "recipe";
    }

}
