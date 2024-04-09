package IvanovVasil.OrderManagmentSystem.Table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Table {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private Long tableNumber;
  @Enumerated(EnumType.STRING)
  private TableState tableState;
}
