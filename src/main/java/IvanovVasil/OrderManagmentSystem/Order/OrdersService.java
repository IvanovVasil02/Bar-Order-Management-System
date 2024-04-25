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
import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
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
      updateOrdersTotalAmount(order);
      updateOrdersRemainingAmount(order);
      Order savedOrder = this.save(order);
      return this.convertOrderResultToDTO(savedOrder);
    } else {
      Order order = table.getOrder();
      for (OrderDetailsDTO odDTO : body.productList()) {
        Product product = ps.findById(odDTO.productId());
        OrderDetails oldOrderDetails = odr.findByProductIdAndOrderId(odDTO.productId(), table.getOrder().getId());

        oldOrderDetails.setQuantity(oldOrderDetails.getQuantity() + odDTO.quantity());
        updateOrderDetailsSubtotal(oldOrderDetails);
        odr.save(oldOrderDetails);
      }
      updateOrdersTotalAmount(order);
      updateOrdersRemainingAmount(order);
      Order savedOrder = this.save(order);
      return this.convertOrderResultToDTO(savedOrder);

    }
  }

  public OrderResultDTO payOrder(UUID id) {
    Order order = or.findById(id).orElseThrow(() -> new NotFoundException(id));
    Table table = order.getTable();
    order.setOrderState(OrderState.COMPLETED);
    order.setRemainingAmountToPay(0.0);
    for (OrderDetails odts : order.getProductList()) {
      updateOrderDetailsSubtotal(odts);
      odr.save(odts);
    }
    table.setTableState(TableState.FREE);
    ts.save(table);
    or.save(order);
    return convertOrderResultToDTO(order);
  }

  public OrderResultDTO payPartialOrder(UUID orderId, List<OrderDetailsDTO> pruductsToPay) {
    Order order = or.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));
    Table table = order.getTable();
    for (OrderDetailsDTO odtDTO : pruductsToPay) {
      OrderDetails orderDetailsFound = odr.findByProductIdAndOrderId(odtDTO.productId(), orderId);
      updateOrderDetailsPaiquantity(orderDetailsFound, odtDTO.quantity());
      odr.save(orderDetailsFound);
    }
    updateOrdersTotalAmount(order);
    updateOrdersRemainingAmount(order);
    or.save(order);
    return convertOrderResultToDTO(order);
  }

  private void delete(Order order) {
    or.deleteById(order.getId());
  }

  public void updateOrdersTotalAmount(Order order) {
    Double totalAmountToPay = order.getProductList().stream().mapToDouble(e -> e.getQuantity() * e.getProduct().getPrice()).sum();
    order.setTotalAmount(totalAmountToPay);
  }

  public void updateOrdersRemainingAmount(Order order) {
    Double remainingAmountToPay = order.getProductList().stream().mapToDouble(OrderDetails::getSubtotal).sum();
    order.setRemainingAmountToPay(remainingAmountToPay);
  }

  public void updateOrderDetailsPaiquantity(OrderDetails orderDetails, long quantity) {
    long paidQuantity = orderDetails.getPaidQuantity() + quantity;
    if (paidQuantity <= orderDetails.getQuantity()) {
      orderDetails.setPaidQuantity(paidQuantity);
      updateOrderDetailsSubtotal(orderDetails);
    }
  }

  public void updateOrderDetailsSubtotal(OrderDetails orderDetails) {
    double updatedSubTotal = (orderDetails.getQuantity() * orderDetails.getProduct().getPrice())
            - (orderDetails.getPaidQuantity() * orderDetails.getProduct().getPrice());
    orderDetails.setSubtotal(updatedSubTotal);
  }


  public OrderResultDTO convertOrderResultToDTO(Order order) {
    return OrderResultDTO
            .builder()
            .order_id(order.getId())
            .table_id(order.getTable().getId())
            .tableNumber(order.getTable().getTableNumber())
            .productList(order.getProductList().stream().map(this::convertOrderDetailsResultDTO).toList())
            .orderState(order.getOrderState())
            .remainingToPay(order.getRemainingAmountToPay())
            .totalPrice(order.getTotalAmount())
            .tableState(order.getTable().getTableState())
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
            .quantity(orderDetails.getQuantity())
            .preparedQuantity(orderDetails.getPreparedQuantity())
            .paidQuantity(orderDetails.getPaidQuantity())
            .productPrice(orderDetails.getProduct().getPrice())
            .subtotal(orderDetails.getSubtotal())
            .localDateTime(orderDetails.getLocalDateTime())
            .build();
  }


}
