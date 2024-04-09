package IvanovVasil.OrderManagmentSystem.Order;

import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsDTO;
import IvanovVasil.OrderManagmentSystem.Product.Product;
import IvanovVasil.OrderManagmentSystem.Product.ProductsService;
import IvanovVasil.OrderManagmentSystem.Table.Table;
import IvanovVasil.OrderManagmentSystem.Table.TablesSerice;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class OrdersService {

  @Autowired
  OrdersRepository or;
  @Autowired
  TablesSerice ts;

  @Autowired
  ProductsService ps;

  @Autowired
  OrderDetailsRepository odr;

  public Order save(Order table) {
    return or.save(table);
  }

  public Order findById(UUID id) {
    return or.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Order> getAllMedicines() {
    return or.findAll();
  }

  private Order createOrder(OrderDTO body) {
    Table table = ts.findById(body.tableId());
    Order order = Order
            .builder()
            .table(table)
            .note(body.note())
            .build();
    for (OrderDetails orderDetail : body.productList()) {
      Product product = ps.findById(orderDetail.getProduct().getId());
      orderDetail.setProduct(product);
      orderDetail.setOrder(order);
      odr.save(orderDetail);
      order.setTotalPrice(order.getTotalPrice() + product.getPrice());
    }
    return this.save(order);
  }

  private void delete(Order order) {
    or.deleteById(order.getId());
  }
}
