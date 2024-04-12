package IvanovVasil.OrderManagmentSystem.Order.entities;

import IvanovVasil.OrderManagmentSystem.Order.enums.OrderDetailState;
import IvanovVasil.OrderManagmentSystem.Product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
  private Long quantity;
  private Long paidQuantity;
  private LocalDateTime localDateTime;
  private double subtotal;

  @Override
  public String toString() {
    return "OrderDetails{" +
            "id=" + id +
            ", product=" + product +
            ", order=" + order +
            '}';
  }
}
