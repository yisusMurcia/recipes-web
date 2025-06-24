package ing.yisus.recipesweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HTMLController {


    @GetMapping("sign-up")
    public String signUp() {
        return "sign-up";
    }
    @GetMapping("login")
    public String login() {
        return "login";
    }

}
