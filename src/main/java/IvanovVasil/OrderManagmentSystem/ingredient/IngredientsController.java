package IvanovVasil.OrderManagmentSystem.ingredient;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/add")
  public Ingredient addIngredient(@RequestBody IngredientDTO body) {
    return is.save(body);
  }

  @PutMapping("/edit")
  public Ingredient editPrdouct(@RequestParam UUID ingredientId, @RequestBody Ingredient body) {
    return is.editIngredient(ingredientId, String.valueOf(body));
  }

  @DeleteMapping("/delete")
  public void deletePrdouct(@RequestParam UUID ingredientId) {
    is.delete(ingredientId);
  }

}
