package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestaurantTable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private static Long totalTables = 0L;
  private Long tableNumber;
  @Enumerated(EnumType.STRING)
  private TableState tableState = TableState.FREE;
  @OneToMany(mappedBy = "table", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Order> orders;

  public RestaurantTable() {
    totalTables++;
    this.tableNumber = totalTables;
  }


}
