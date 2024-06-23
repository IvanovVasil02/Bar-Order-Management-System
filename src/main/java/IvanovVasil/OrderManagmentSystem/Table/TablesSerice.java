package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.OrdersService;
import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessageService;
import IvanovVasil.OrderManagmentSystem.webSocket.ElementToUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TablesSerice {

  @Autowired
  TablesRepository tr;
  @Autowired
  OrdersService os;

  @Autowired
  ChatMessageService cms;

  public void save(Table restaurantTable) {
    tr.save(restaurantTable);
  }

  public Table findById(UUID id) {
    return tr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<TableResultDTO> getAllTables() {
    return tr.findAll().stream().map(this::convetToTableDTO).toList();
  }

  public Table createTable() {
    Table restaurantTable = new Table();
    tr.save(restaurantTable);
    cms.sendUpdateMessage(ElementToUp.TABLE);
    return restaurantTable;
  }

  public List<TableResultDTO> createTables(int num) {
    List<TableResultDTO> tableList = new ArrayList<>();
    for (int i = 0; i < num - 1; i++) {
      tableList.add(convetToTableDTO(createTable()));
    }
    cms.sendUpdateMessage(ElementToUp.TABLE);
    return tableList;
  }

  private void delete(Table restaurantTable) {
    tr.deleteById(restaurantTable.getId());
    cms.sendUpdateMessage(ElementToUp.TABLE);
  }

  public void deleteTableById(UUID id) {
    tr.deleteById(id);
    cms.sendUpdateMessage(ElementToUp.TABLE);
  }


  public void deleteTableByTableNumber(Long tableNumber) {
    tr.deleteAllByTableNumber(tableNumber);
    cms.sendUpdateMessage(ElementToUp.TABLE);
  }

  public TableResultDTO convetToTableDTO(Table table) {
    TableResultDTO.TableResultDTOBuilder builder =
            TableResultDTO.builder()
                    .table_id(table.getId())
                    .tableNumber(table.getTableNumber())
                    .tableState(table.getTableState());
    Order pendingOrder = os.findByOrderState(OrderState.PENDING, table.getId());

    if (pendingOrder != null) {
      OrderResultDTO orderDTO = os.convertOrderResultToDTO(pendingOrder);
      builder.order(orderDTO);
    } else {
      builder.order(null);
    }
    return builder.build();
  }
}
