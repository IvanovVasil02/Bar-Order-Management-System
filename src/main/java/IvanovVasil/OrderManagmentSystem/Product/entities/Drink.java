package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.DrinkCategory;
import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("drink")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "drinks")
public class Drink extends Product {
  @Enumerated(EnumType.STRING)
  private DrinkCategory subCategory;
  private Long quantity;

  public Drink(String name, Double price, DrinkCategory subCategory, Long quantity, User user) {
    super(name, price, user);
    this.setProductCategory(ProductCategory.DRINK);
    this.subCategory = subCategory;
    this.quantity = quantity;
  }
}
