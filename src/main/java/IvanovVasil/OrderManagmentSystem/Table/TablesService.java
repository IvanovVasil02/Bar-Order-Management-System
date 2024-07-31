package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.OrdersService;
import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.User.UsersService;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessageService;
import IvanovVasil.OrderManagmentSystem.webSocket.ElementToUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TablesService {

  @Autowired
  TablesRepository tr;
  @Autowired
  OrdersService os;
  @Autowired
  UsersService us;

  @Autowired
  ChatMessageService cms;

  public void save(Table restaurantTable) {
    tr.save(restaurantTable);
  }

  public Table findById(UUID id) {
    return tr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<TableResultDTO> getAllTables(User user) {
    User verifiedUser = us.findById(user.getId());
    return tr.findAllByUserId(verifiedUser.getId()).stream().map(this::convetToTableDTO).toList();
  }

  public Table createTable(User user) {
    Table restaurantTable = new Table(user);
    tr.save(restaurantTable);
    cms.sendUpdateMessage(ElementToUp.TABLE);
    return restaurantTable;
  }

  public List<TableResultDTO> createTables(int num, User user) {
    User verifiedUser = us.findById(user.getId());
    List<TableResultDTO> tableList = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      tableList.add(convetToTableDTO(createTable(verifiedUser)));
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


  public void deleteTableByTableNumber(Long tableNumber, User user) {
    User verifiedUser = us.findById(user.getId());
    Table table = tr.findByTableNumberAndUserId(tableNumber, user.getId());
    if (table == null) {
      throw new BadRequestException("Item not found");
    }
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
