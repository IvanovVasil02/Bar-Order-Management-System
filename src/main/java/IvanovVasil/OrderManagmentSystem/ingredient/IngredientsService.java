package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessageService;
import IvanovVasil.OrderManagmentSystem.webSocket.ElementToUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientsService {
  @Autowired
  IngredientsRepository ir;

  @Autowired
  ChatMessageService cms;

  public List<Ingredient> getAllIngredients() {
    return ir.findAll();
  }

  public Ingredient save(Ingredient ingredient) {

    return ir.save(ingredient);
  }

  public Ingredient save(IngredientDTO ingredient) {
    Ingredient ingredientFound = ir.findByNameIgnoreCase(ingredient.ingredientName());

    if (ingredientFound != null) {
      throw new IllegalArgumentException("Ingredient already exists");
    }

    Ingredient newIngredient = Ingredient
            .builder()
            .name(ingredient.ingredientName())
            .ingredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()))
            .build();
    ir.save(newIngredient);
    cms.sendUpdateMessage(ElementToUp.INGREDIENT);
    return newIngredient;

  }

  public Ingredient findById(UUID ingredientId) {
    return ir.findById(ingredientId).orElseThrow(() -> new NotFoundException(ingredientId));
  }

  public Ingredient editIngredient(UUID ingredientId, IngredientDTO ingredient) {
    Ingredient ingredientFound = this.findById(ingredientId);
    if (!ingredient.ingredientName().isEmpty()) ingredientFound.setName(ingredient.ingredientName());
    if (!ingredient.ingredientCategory().isEmpty())
      ingredientFound.setIngredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()));
    ir.save(ingredientFound);

    cms.sendUpdateMessage(ElementToUp.INGREDIENT);
    return ingredientFound;
  }

  public void delete(UUID ingredientID) {
    ir.deleteById(ingredientID);
    cms.sendUpdateMessage(ElementToUp.INGREDIENT);
  }

  public List<Ingredient> getAllIngredientsByCategory() {
    return null;
  }
}
