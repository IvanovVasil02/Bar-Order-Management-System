package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.exceptions.UnauthorizedException;
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

  public List<Ingredient> getAllIngredients(User user) {
    return ir.findAllByUserId(user.getId());
  }

  public Ingredient save(Ingredient ingredient) {
    return ir.save(ingredient);
  }

  public Ingredient save(IngredientDTO ingredient, User user) {
    Ingredient ingredientFound = ir.findByNameIgnoreCase(ingredient.ingredientName());
    Ingredient newIngredient = Ingredient
            .builder()
            .name(ingredient.ingredientName())
            .ingredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()))
            .user(user)
            .build();
    ir.save(newIngredient);
    cms.sendUpdateMessage(ElementToUp.INGREDIENT);
    return newIngredient;
  }

  public Ingredient findById(UUID ingredientId) {
    return ir.findById(ingredientId).orElseThrow(() -> new NotFoundException(ingredientId));
  }

  public Ingredient editIngredient(UUID ingredientId, IngredientDTO ingredient, User user) {
    Ingredient ingredientFound = this.findById(ingredientId);
    if (ingredientFound.getUser().getId() == user.getId()) {
      if (!ingredient.ingredientName().isEmpty()) ingredientFound.setName(ingredient.ingredientName());
      ingredientFound.setIngredientCategory(IngredientCategory.valueOf(ingredient.ingredientCategory()));
      ir.save(ingredientFound);

      cms.sendUpdateMessage(ElementToUp.INGREDIENT);
      return ingredientFound;
    } else {
      throw new UnauthorizedException("You have not permissions to edit this item!");
    }

  }

  public void delete(UUID ingredientID, User user) {
    Ingredient found = this.findById(ingredientID);

    if (found.getUser().getId() == user.getId()) {
      if (ingredientID != null) {
        ir.deleteById(ingredientID);
        cms.sendUpdateMessage(ElementToUp.INGREDIENT);
      } else {
        throw new BadRequestException("Ingredient id cannot be null");
      }
    } else {
      throw new UnauthorizedException("You have not the permissions to delete this ingredient!");
    }

  }

  public List<Ingredient> getAllIngredientsByCategory(User user) {
    return null;
  }
}
