package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@NoArgsConstructor
public class RestUserControl {

    @GetMapping("/user")//For validate if user is logged in, it returns the username, if is not logged, return null
    public Object getUser(HttpSession session) {
        return session.getAttribute("userName");
    }
}
