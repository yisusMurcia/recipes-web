package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.Dto.UserDto;
import ing.yisus.recipesweb.model.User;
import ing.yisus.recipesweb.persistence.UserEntity;
import ing.yisus.recipesweb.service.RecipeService;
import ing.yisus.recipesweb.service.UserService;
import ing.yisus.recipesweb.util.UserMapper;
import ing.yisus.recipesweb.util.RecipeMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class Control {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping("sign-up")
    public String signUp(Model model) {
        return "sign-up";
    }
    @GetMapping("login")
    public String login() {
        return "login";
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
    public String recipe(@PathVariable long id, RedirectAttributes redirectAttributes) {
        if(id >= recipeService.getRecipeCount()){
            id = recipeService.getRecipeCount();
        }
        redirectAttributes.addFlashAttribute("id", id);
        return "recipe";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }
}

