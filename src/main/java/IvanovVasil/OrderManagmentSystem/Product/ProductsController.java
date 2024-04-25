package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {

  @Autowired
  ProductsService ps;

  @GetMapping("")
  public List<Product> getAllProducts() {
    return ps.getAllProducts();
  }
  

  @PostMapping("/addProduct")
  public Product createProduct(@RequestBody ProductDTO body) {
    System.out.println(body);
    Product product = ps.createProduct(body);
    return ResponseEntity.ok(product).getBody();
  }

  @DeleteMapping("/deleteProduct")
  public void createProduct(UUID id) {
    ps.delete(id);
  }

}
