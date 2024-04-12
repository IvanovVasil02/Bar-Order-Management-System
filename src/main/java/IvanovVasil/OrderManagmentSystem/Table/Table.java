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
public class Table {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private static Long totalTables = 0L;
  private Long tableNumber;
  @Enumerated(EnumType.STRING)
  private TableState tableState = TableState.FREE;
  @OneToOne(mappedBy = "table")
  private Order order;

  public Table() {
    totalTables++;
    this.tableNumber = totalTables;
  }


}
