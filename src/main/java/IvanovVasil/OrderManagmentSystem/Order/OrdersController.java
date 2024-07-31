package IvanovVasil.OrderManagmentSystem.Order;

import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.Table.TableResultDTO;
import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {
  @Autowired
  OrdersService os;

  @GetMapping("")
  public Page<OrderResultDTO> getOrdersByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size,
                                              @RequestParam(defaultValue = "dateTime") String orderBy, @AuthenticationPrincipal User user) {
    return os.getAllOrdersByDate(date, page, size, orderBy, user);
  }

  @GetMapping("/{tableid}")
  public List<OrderResultDTO> getSingleTableOrders(@PathVariable UUID tableid) {
    return os.getSingleTableOrders(tableid);
  }

  @PostMapping("")
  public TableResultDTO addOrder(@Valid @RequestBody OrderDTO body, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {
      return os.createOrder(body, user);
    }
  }

  @PutMapping("/payOrder/{orderId}")
  public TableResultDTO payOrder(@PathVariable UUID orderId, @AuthenticationPrincipal User user) {
    return os.payOrder(orderId, user);
  }

  @PutMapping("/addToOrder/{orderId}")
  public TableResultDTO addToOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderDetailsDTO product, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {
      return os.addToOrder(orderId, product, user);
    }
  }

  @PutMapping("/payPartialOrder/{orderId}")
  public TableResultDTO payPartialOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderDetailsDTO product, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {

      return os.payPartialOrder(orderId, product, user);
    }
  }

}
