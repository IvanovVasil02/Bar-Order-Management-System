package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.DrinkCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@DiscriminatorValue("drink")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Drink extends Product {
  @Enumerated(EnumType.STRING)
  private DrinkCategory category;
  private Long quantity;

  public Drink(String name, Double price, DrinkCategory category, Long quantity) {
    super(name, price);
    this.category = category;
    this.quantity = quantity;
  }
}
