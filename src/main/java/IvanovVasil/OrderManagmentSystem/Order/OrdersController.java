package IvanovVasil.OrderManagmentSystem.Order;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {
  @Autowired
  OrdersService os;

  @GetMapping("")
  public List<OrderResultDTO> getOrders() {
    return os.getAllOrders();
  }

  @GetMapping("/{tableid}")
  public List<OrderResultDTO> getSingleTableOrders(@PathVariable UUID tableid) {
    return os.getSingleTableOrders(tableid);
  }

  @PostMapping("/addOrder")
  public OrderResultDTO addOrder(@RequestBody OrderDTO body) {
    return os.createOrder(body);
  }

  @PutMapping("/payOrder/{orderId}")
  public OrderResultDTO payOrder(@PathVariable UUID orderId) {
    return os.payOrder(orderId);
  }

  @PutMapping("/payPartialOrder/{orderId}")
  public OrderResultDTO payPartialOrder(@PathVariable UUID orderId, @RequestBody List<OrderDetailsDTO> body) {
    return os.payPartialOrder(orderId, body);
  }


}
