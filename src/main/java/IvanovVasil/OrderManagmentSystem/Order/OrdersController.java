package IvanovVasil.OrderManagmentSystem.Order;

import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
                                              @RequestParam(defaultValue = "dateTime") String orderBy) {
    return os.getAllOrdersByDate(date, page, size, orderBy);
  }

  @GetMapping("/{tableid}")
  public List<OrderResultDTO> getSingleTableOrders(@PathVariable UUID tableid) {
    return os.getSingleTableOrders(tableid);
  }

  @PostMapping("")
  public OrderResultDTO addOrder(@Valid @RequestBody OrderDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {
      return os.createOrder(body);
    }
  }

  @PutMapping("/payOrder/{orderId}")
  public OrderResultDTO payOrder(@PathVariable UUID orderId) {
    return os.payOrder(orderId);
  }

  @PutMapping("/addToOrder/{orderId}")
  public OrderResultDTO addToOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderDetailsDTO product, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {
      return os.addToOrder(orderId, product);
    }
  }

  @PutMapping("/payPartialOrder/{orderId}")
  public OrderResultDTO payPartialOrder(@PathVariable UUID orderId, @Valid @RequestBody OrderDetailsDTO product, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the data you submitted", validation.getAllErrors());
    } else {
      return os.payPartialOrder(orderId, product);
    }
  }

}
