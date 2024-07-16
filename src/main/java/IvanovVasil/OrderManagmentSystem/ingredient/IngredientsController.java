package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
  @Autowired
  IngredientsService is;

  @GetMapping("")
  public List<Ingredient> getAll(@AuthenticationPrincipal User user) {
    return is.getAllIngredients(user);
  }

  @GetMapping("/{category}")
  public List<Ingredient> getAllIngredientsByCategory(@PathParam("category") String category, @AuthenticationPrincipal User user) {
    return is.getAllIngredientsByCategory(user);
  }

  @PostMapping("")
  public Ingredient addIngredient(@Valid @RequestBody IngredientDTO body, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("empty field", validation.getAllErrors());
    } else {
      return is.save(body, user);
    }
  }

  @PutMapping("")
  public Ingredient editIngredient(@RequestParam UUID ingredientId, @Valid @RequestBody IngredientDTO body, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("empty field", validation.getAllErrors());
    } else {
      return is.editIngredient(ingredientId, body, user);
    }
  }

  @DeleteMapping("")
  public void deleteIngredient(@RequestParam UUID ingredientId, @AuthenticationPrincipal User user) {
    is.delete(ingredientId, user);
  }

}
