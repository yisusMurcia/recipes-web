package ing.yisus.recipesweb.controller;

import ing.yisus.recipesweb.model.FoodIntention;
import ing.yisus.recipesweb.model.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnumController {
    @GetMapping("food-intentions")
    public ResponseEntity<FoodIntention[]> getFoodIntentions() {
        return ResponseEntity.ok(FoodIntention.values());
    }

    @GetMapping("food-types")
    public ResponseEntity<FoodType[]> getFoodTypes() {
        return ResponseEntity.ok(FoodType.values());
    }
}