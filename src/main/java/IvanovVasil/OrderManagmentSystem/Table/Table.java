package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@jakarta.persistence.Table(name = "restaurant_table")
public class Table {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private static Long totalTables = 0L;
  private Long tableNumber;
  @Enumerated(EnumType.STRING)
  private TableState tableState = TableState.FREE;
  @OneToMany(mappedBy = "table", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<Order> orders;
  @ManyToOne
  private User user;

  public Table(User user) {
    totalTables++;
    this.tableNumber = totalTables;
    this.user = user;
  }


}
