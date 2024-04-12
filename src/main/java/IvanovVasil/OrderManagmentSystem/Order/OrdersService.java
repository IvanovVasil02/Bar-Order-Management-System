package IvanovVasil.OrderManagmentSystem.Order;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.entities.OrderDetails;
import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderDetailsResultDTO;
import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.Order.repositories.OrderDetailsRepository;
import IvanovVasil.OrderManagmentSystem.Order.repositories.OrdersRepository;
import IvanovVasil.OrderManagmentSystem.Product.Product;
import IvanovVasil.OrderManagmentSystem.Product.ProductsService;
import IvanovVasil.OrderManagmentSystem.Table.Table;
import IvanovVasil.OrderManagmentSystem.Table.TableState;
import IvanovVasil.OrderManagmentSystem.Table.TablesSerice;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
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

  public List<OrderResultDTO> getAllOrders() {
    return or.findAll().stream().map(this::convertOrderResultToDTO).toList();
  }

  public List<OrderResultDTO> getSingleTableOrders(UUID tableid) {
    return or.findByTableId(tableid).stream().map(this::convertOrderResultToDTO).toList();
  }

  public OrderResultDTO createOrder(OrderDTO body) {
    Table table = ts.findById(body.tableId());

    if (table.getTableState() != TableState.TAKEN) {
      List<OrderDetails> orderDetailsList = new ArrayList<>();
      table.setTableState(TableState.TAKEN);
      double totalAmount = 0.0;
      Order order = Order
              .builder()
              .table(table)
              .dateTime(LocalDateTime.now())
              .note(body.note())
              .orderState(OrderState.PENDING)
              .build();
      this.save(order);
      for (OrderDetailsDTO odDTO : body.productList()) {
        Product product = ps.findById(odDTO.productId());
        OrderDetails newOrderDetails = OrderDetails
                .builder()
                .order(order)
                .product(product)
                .quantity(odDTO.quantity())
                .paidQuantity(0L)
                .localDateTime(LocalDateTime.now())
                .subtotal(product.getPrice() * odDTO.quantity())
                .build();
        odr.save(newOrderDetails);
        totalAmount += product.getPrice() * odDTO.quantity();
        orderDetailsList.add(newOrderDetails);
      }
      order.setProductList(orderDetailsList);
      order.setTotalAmount(totalAmount);
      order.setRemainingAmountToPay(totalAmount);
      Order savedOrder = this.save(order);
      return this.convertOrderResultToDTO(savedOrder);
    } else {
      Order order = table.getOrder();
      for (OrderDetailsDTO odDTO : body.productList()) {
        Product product = ps.findById(odDTO.productId());
        OrderDetails oldOrderDetails = odr.findByProductIdAndOrderId(odDTO.productId(), table.getOrder().getId());

        oldOrderDetails.setQuantity(oldOrderDetails.getQuantity() + odDTO.quantity());
        double newSubtotal = this.getOrderDetailsSubtotal(oldOrderDetails);
        oldOrderDetails.setSubtotal(newSubtotal);
        odr.save(oldOrderDetails);
      }
      order.setTotalAmount(getOrdersTotalAmount(order));
      Order savedOrder = this.save(order);
      return this.convertOrderResultToDTO(savedOrder);

    }
  }

  private void delete(Order order) {
    or.deleteById(order.getId());
  }

  public Order paidOrder(UUID id) {
    Order order = or.findById(id).orElseThrow(() -> new NotFoundException(id));
    Table table = order.getTable();
    order.setOrderState(ORde);

    table.setTableState(TableState.FREE);
    return order;
  }

  public Double getOrdersTotalAmount(Order order) {
    return order.getProductList().stream().mapToDouble(OrderDetails::getSubtotal).sum();
  }

  public double getOrderDetailsSubtotal(OrderDetails orderDetails) {
    return (orderDetails.getQuantity() * orderDetails.getProduct().getPrice())
            - (orderDetails.getPaidQuantity() * orderDetails.getProduct().getPrice());
  }


  public OrderResultDTO convertOrderResultToDTO(Order order) {
    return OrderResultDTO
            .builder()
            .order_id(order.getId())
            .table_id(order.getTable().getId())
            .tableNumber(order.getTable().getTableNumber())
            .productList(order.getProductList().stream().map(this::convertOrderDetailsResultDTO).toList())
            .totalPrice(order.getTotalAmount())
            .build();
  }

  public OrderDetailsResultDTO convertOrderDetailsResultDTO(OrderDetails orderDetails) {
    return OrderDetailsResultDTO
            .builder()
            .orderId(orderDetails.getOrder().getId())
            .tableId(orderDetails.getOrder().getTable().getId())
            .tableNumber(orderDetails.getOrder().getTable().getTableNumber())
            .productId(orderDetails.getProduct().getId())
            .productName(orderDetails.getProduct().getName())
            .productDescription(orderDetails.getProduct().getDescription())
            .quantity(orderDetails.getQuantity())
            .paidQuantity(orderDetails.getPaidQuantity())
            .productPrice(orderDetails.getProduct().getPrice())
            .subtotal(orderDetails.getSubtotal())
            .localDateTime(orderDetails.getLocalDateTime())
            .build();
  }


}
