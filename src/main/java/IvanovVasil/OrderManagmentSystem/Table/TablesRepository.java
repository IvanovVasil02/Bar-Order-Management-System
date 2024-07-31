package IvanovVasil.OrderManagmentSystem.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public interface TablesRepository extends JpaRepository<Table, UUID> {

  void deleteAllByTableNumber(Long tableNumber);

  Table findByTableNumberAndUserId(Long tableNumber, UUID id);

  List<Table> findAllByUserId(UUID id);
}
