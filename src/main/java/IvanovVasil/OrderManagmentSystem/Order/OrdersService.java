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
import IvanovVasil.OrderManagmentSystem.Table.TablesRepository;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessageService;
import IvanovVasil.OrderManagmentSystem.webSocket.ElementToUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {

  @Autowired
  OrdersRepository or;
  @Autowired
  TablesRepository ts;

  @Autowired
  ProductsService ps;

  @Autowired
  OrderDetailsRepository odr;

  @Autowired
  private ChatMessageService cms;

  public Order save(Order order) {
    return or.save(order);
  }

  public Order findById(UUID id) {
    return or.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Order findByOrderState(OrderState state, UUID tableId) {
    return or.findByOrderStateAndTableId(state, tableId);
  }

  public List<OrderResultDTO> getAllOrders() {
    return or.findAll().stream().map(this::convertOrderResultToDTO).toList();
  }

  public Page<OrderResultDTO> getAllOrdersByDate(LocalDate date, int page, int size, String orderBy) {
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    Page<Order> orderList = or.findByDateTimeBetween(startOfDay, endOfDay, pageable);
    return orderList.map(this::convertOrderResultToDTO);
  }

  public List<OrderResultDTO> getSingleTableOrders(UUID tableid) {
    return or.findByTableId(tableid).stream().map(this::convertOrderResultToDTO).toList();
  }

  public OrderResultDTO createOrder(OrderDTO body) {
    Table restaurantTable = ts.findById(body.tableId()).orElseThrow(() -> new NotFoundException(body.tableId()));
    Order order;
    List<OrderDetails> orderDetailsList = new ArrayList<>();
    double totalAmount = 0.0;


    if (restaurantTable.getTableState() == TableState.FREE) {

      restaurantTable.setTableState(TableState.TAKEN);


      order = Order.builder()
              .table(restaurantTable)
              .dateTime(LocalDateTime.now())
              .note(body.note())
              .orderState(OrderState.PENDING)
              .build();
      this.save(order);

      for (OrderDetailsDTO odDTO : body.productList()) {
        Product product = ps.findById(odDTO.id());
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

    } else if (restaurantTable.getTableState() == TableState.TAKEN) {
      order = this.findByOrderState(OrderState.PENDING, restaurantTable.getId());

      for (OrderDetailsDTO odDTO : body.productList()) {
        Product product = ps.findById(odDTO.id());
        OrderDetails oldOrderDetails = odr.findByProductIdAndOrderId(odDTO.id(), order.getId());

        if (oldOrderDetails != null) {
          oldOrderDetails.setQuantity(oldOrderDetails.getQuantity() + odDTO.quantity());
          updateOrderDetailsSubtotal(oldOrderDetails);
          odr.save(oldOrderDetails);
        } else {
          OrderDetails newOrderDetails = OrderDetails.builder()
                  .order(order)
                  .product(product)
                  .quantity(odDTO.quantity())
                  .paidQuantity(0L)
                  .localDateTime(LocalDateTime.now())
                  .subtotal(product.getPrice() * odDTO.quantity())
                  .build();
          odr.save(newOrderDetails);
          orderDetailsList.add(newOrderDetails);
        }
      }
      order.setProductList(orderDetailsList);
      updateOrdersTotalAmount(order);
      updateOrdersRemainingAmount(order);

      this.save(order);

    } else {
      throw new IllegalStateException("Invalid table state: " + restaurantTable.getTableState());
    }

    cms.sendUpdateMessage(ElementToUp.ORDER);
    return convertOrderResultToDTO(order);
  }

  public OrderResultDTO payOrder(UUID id) {
    Order order = or.findById(id).orElseThrow(() -> new NotFoundException(id));
    Table restaurantTable = order.getTable();
    order.setOrderState(OrderState.COMPLETED);
    order.setRemainingAmountToPay(0.0);
    for (OrderDetails odts : order.getProductList()) {
      updateOrderDetailsSubtotal(odts);
      odr.save(odts);
    }
    restaurantTable.setOrders(null);
    restaurantTable.setTableState(TableState.FREE);
    ts.save(restaurantTable);
    or.save(order);
    cms.sendUpdateMessage(ElementToUp.INGREDIENT);
    return convertOrderResultToDTO(order);
  }

  public OrderResultDTO addToOrder(UUID orderId, OrderDetailsDTO product) {
    Order order = or.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));

    OrderDetails orderDetailsFound = odr.findByProductIdAndOrderId(product.id(), orderId);
    orderDetailsFound.setQuantity(orderDetailsFound.getQuantity() + product.quantity());
    odr.save(orderDetailsFound);

    updateOrdersTotalAmount(order);
    updateOrdersRemainingAmount(order);
    or.save(order);
    cms.sendUpdateMessage(ElementToUp.ORDER);
    return convertOrderResultToDTO(order);
  }

  public OrderResultDTO payPartialOrder(UUID orderId, OrderDetailsDTO pruductToPay) {
    Order order = or.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));

    OrderDetails orderDetailsFound = odr.findByProductIdAndOrderId(pruductToPay.id(), orderId);
    updateOrderDetailsPaiquantity(orderDetailsFound, pruductToPay.quantity());
    odr.save(orderDetailsFound);

    updateOrdersTotalAmount(order);
    updateOrdersRemainingAmount(order);
    or.save(order);
    cms.sendUpdateMessage(ElementToUp.ORDER);
    return convertOrderResultToDTO(order);
  }

  private void delete(Order order) {
    or.deleteById(order.getId());
    cms.sendUpdateMessage(ElementToUp.ORDER);
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
            .dateTime(order.getDateTime())
            .build();
  }

  public OrderDetailsResultDTO convertOrderDetailsResultDTO(OrderDetails orderDetails) {
    return OrderDetailsResultDTO
            .builder()
            .orderId(orderDetails.getOrder().getId())
            .tableId(orderDetails.getOrder().getTable().getId())
            .tableNumber(orderDetails.getOrder().getTable().getTableNumber())
            .id(orderDetails.getProduct().getId())
            .name(orderDetails.getProduct().getName())
            .quantity(orderDetails.getQuantity())
            .preparedQuantity(orderDetails.getPreparedQuantity())
            .paidQuantity(orderDetails.getPaidQuantity())
            .price(orderDetails.getProduct().getPrice())
            .subtotal(orderDetails.getSubtotal())
            .localDateTime(orderDetails.getLocalDateTime())
            .build();
  }


}
