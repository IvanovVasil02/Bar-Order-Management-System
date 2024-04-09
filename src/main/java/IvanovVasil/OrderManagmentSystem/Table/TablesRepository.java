package IvanovVasil.OrderManagmentSystem.Table;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TablesRepository extends JpaRepository<Table, UUID> {
}
