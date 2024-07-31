package IvanovVasil.OrderManagmentSystem.Order.repositories;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Order, UUID> {
  List<Order> findByTableId(UUID tableid);

  Order findByOrderStateAndTableId(OrderState state, UUID tableId);


  Page<Order> findByDateTimeBetweenAndUserId(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable, UUID user);
}
