package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ProductsService {

  @Autowired
  ProductsRepository pr;

  public void save(Product product){
    pr.save(product);
  }

  public Product findById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Product> getAllMedicines() {
    return pr.findAll();
  }

  private Product createProduct(String name, String description, Double price, Long quantity){
    Product product = new Product(name, description, price, quantity);
    return pr.save(product);
  }

  private void delete (Product product){
    pr.deleteById(product.getId());
  }
}
