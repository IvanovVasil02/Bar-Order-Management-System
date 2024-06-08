package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.OrdersService;
import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TablesSerice {

  @Autowired
  TablesRepository tr;
  @Autowired
  OrdersService os;

  public void save(RestaurantTable restaurantTable) {
    tr.save(restaurantTable);
  }

  public RestaurantTable findById(UUID id) {
    return tr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<TableResultDTO> getAllTables() {
    return tr.findAll().stream().map((table) -> {
              TableResultDTO.TableResultDTOBuilder builder = TableResultDTO
                      .builder()
                      .table_id(table.getId())
                      .tableState(table.getTableState())
                      .tableNumber(table.getTableNumber());

              Order pendingOrder = os.findByOrderState(OrderState.PENDING, table.getId());

              if (pendingOrder != null) {
                OrderResultDTO orderDTO = os.convertOrderResultToDTO(pendingOrder);
                builder.order(orderDTO);
              } else {
                builder.order(null);
              }

              return builder.build();
            }
    ).toList();
  }

  public RestaurantTable createTable() {
    RestaurantTable restaurantTable = new RestaurantTable();
    return tr.save(restaurantTable);
  }

  public List<RestaurantTable> createTables(int num) {
    for (int i = 0; i < num - 1; i++) {
      createTable();
    }
    return tr.findAll();
  }

  private void delete(RestaurantTable restaurantTable) {
    tr.deleteById(restaurantTable.getId());
  }

  public void deleteTableById(UUID id) {
    tr.deleteById(id);
  }


  public void deleteTableByTableNumber(Long tableNumber) {
    tr.deleteAllByTableNumber(tableNumber);
  }
}
