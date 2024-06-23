package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type",
        discriminatorType = DiscriminatorType.STRING)
@Builder
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;
  private Double price;
  private ProductCategory productCategory;

  public Product(String name, Double price) {
    this.name = name;
    this.price = price;
  }


  @Override
  public String toString() {
    return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
  }
}
