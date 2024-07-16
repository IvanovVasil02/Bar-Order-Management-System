package IvanovVasil.OrderManagmentSystem.Product.entities;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.User.User;
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
  @Enumerated(EnumType.STRING)
  private ProductCategory productCategory;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Product(String name, Double price, User user) {
    this.name = name;
    this.price = price;
    this.user = user;
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
