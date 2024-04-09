package IvanovVasil.OrderManagmentSystem.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
}
