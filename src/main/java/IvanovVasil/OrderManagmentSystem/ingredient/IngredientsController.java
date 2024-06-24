package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
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
  public List<Ingredient> getAll() {
    return is.getAllIngredients();
  }

  @GetMapping("/{category}")
  public List<Ingredient> getAllIngredientsByCategory(@PathParam("category") String category) {
    return is.getAllIngredientsByCategory();
  }

  @PostMapping("")
  public Ingredient addIngredient(@Valid @RequestBody IngredientDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("empty field", validation.getAllErrors());
    } else {
      return is.save(body);
    }
  }

  @PutMapping("")
  public Ingredient editIngredient(@RequestParam UUID ingredientId, @Valid @RequestBody IngredientDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("empty field", validation.getAllErrors());
    } else {
      return is.editIngredient(ingredientId, body);
    }
  }

  @DeleteMapping("")
  public void deleteIngredient(@RequestParam UUID ingredientId) {
    is.delete(ingredientId);
  }

}
