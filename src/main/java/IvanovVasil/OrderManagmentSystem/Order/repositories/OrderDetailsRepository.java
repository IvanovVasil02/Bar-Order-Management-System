package IvanovVasil.OrderManagmentSystem.Order.repositories;

import IvanovVasil.OrderManagmentSystem.Order.entities.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {

  OrderDetails findByProductIdAndOrderId(UUID uuid, UUID id);
}
