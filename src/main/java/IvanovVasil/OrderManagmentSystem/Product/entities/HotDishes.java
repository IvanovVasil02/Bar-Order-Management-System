package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.HotDishesCategory;
import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
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
  private HotDishesCategory subCategory;
  @ManyToMany
  @JoinTable(
          name = "hot_dishes_ingredients",
          joinColumns = @JoinColumn(name = "hot_dishes_id"),
          inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
  private List<Ingredient> ingredients;

  public HotDishes(String name, Double price, HotDishesCategory subCategory, List<Ingredient> ingredients) {
    super(name, price);
    this.setProductCategory(ProductCategory.HOT_DISHES);
    this.subCategory = subCategory;
    this.ingredients = ingredients;
  }


}
