package IvanovVasil.OrderManagmentSystem.Order.entities;

import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Table.Table;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @ManyToOne
  @JoinColumn(name = "table_id")
  private Table table;
  private LocalDateTime dateTime;
  @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<OrderDetails> productList = new ArrayList<>();
  private String note;
  private Double totalAmount = 0.00;
  private Double remainingAmountToPay;
  @Enumerated(EnumType.STRING)
  private OrderState orderState;

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", table=" + table.getTableNumber() +
            ", dateTime=" + dateTime +
            ", note='" + note + '\'' +
            ", totalPrice=" + totalAmount +
            '}';
  }


}
