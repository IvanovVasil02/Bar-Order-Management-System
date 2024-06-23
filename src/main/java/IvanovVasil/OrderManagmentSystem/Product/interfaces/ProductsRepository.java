package IvanovVasil.OrderManagmentSystem.Product.interfaces;

import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {

}
