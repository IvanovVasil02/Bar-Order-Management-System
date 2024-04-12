package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductsService {

  @Autowired
  ProductsRepository pr;

  public void save(Product product) {
    pr.save(product);
  }

  public Product findById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Product> getAllProducts() {
    return pr.findAll();
  }

  public Product createProduct(String name, String description, Double price, Long quantity) {
    Product product = Product.builder()
            .name(name)
            .description(description)
            .price(price)
            .quantity(quantity)
            .build();
    return pr.save(product);
  }

  public List<ProductDTO> getProductByName(String query) {
    return pr.findByNameIgnoreCaseContaining(query);
  }

  protected void delete(UUID id) {
    pr.deleteById(id);
  }
}
