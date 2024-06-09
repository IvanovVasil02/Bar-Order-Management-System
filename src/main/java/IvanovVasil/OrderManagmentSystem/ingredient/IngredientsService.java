package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientsService {
  @Autowired
  IngredientsRepository ir;

  public List<Ingredient> getAllIngredients() {
    return ir.findAll();
  }

  public Ingredient save(Ingredient ingredient) {

    return ir.save(ingredient);
  }

  public Ingredient save(IngredientDTO ingredient) {
    Ingredient ingredientFound = ir.findByNameIgnoreCase(ingredient.ingredientName());

    if (ingredientFound != null) {
      return null;
    }
    Ingredient newIngredient = Ingredient
            .builder()
            .name(ingredient.ingredientName())
            .ingredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()))
            .build();
    return ir.save(newIngredient);
  }

  public Ingredient findById(UUID ingredientId) {
    return ir.findById(ingredientId).orElseThrow(() -> new NotFoundException(ingredientId));
  }

  public Ingredient editIngredient(UUID ingredientId, IngredientDTO ingredient) {
    Ingredient ingredientFound = this.findById(ingredientId);
    if (!ingredient.ingredientName().isEmpty()) ingredientFound.setName(ingredient.ingredientName());
    if (!ingredient.ingredientCategory().isEmpty())
      ingredientFound.setIngredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()));
    return ir.save(ingredientFound);
  }

  public void delete(UUID ingredientID) {
    ir.deleteById(ingredientID);
  }

  public List<Ingredient> getAllIngredientsByCategory() {
    return null;
  }
}
