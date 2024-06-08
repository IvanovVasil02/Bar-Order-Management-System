package IvanovVasil.OrderManagmentSystem.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TablesRepository extends JpaRepository<RestaurantTable, UUID> {

  void deleteAllByTableNumber(Long tableNumber);
}
