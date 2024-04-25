package IvanovVasil.OrderManagmentSystem.Product.interfaces;

import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
  
}
