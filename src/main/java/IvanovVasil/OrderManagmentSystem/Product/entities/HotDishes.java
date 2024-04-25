package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.HotDishesCategory;
import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("hot_dishes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotDishes extends Product {
  @Enumerated(EnumType.STRING)
  private HotDishesCategory category;
  @OneToMany
  private List<Ingredient> ingredients;

  public HotDishes(String name, Double price, HotDishesCategory category, List<Ingredient> ingredients) {
    super(name, price);
    this.category = category;
    this.ingredients = ingredients;
  }


}
