package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/search")
  public List<ProductDTO> getProductsByName(@RequestParam String query) {
    return ps.getProductByName(query);
  }

  @PostMapping("/addProduct")
  public void createProduct(ProductDTO body) {
    ps.createProduct(body.name(), body.description(), body.price(), body.quantity());
  }

  @PostMapping("/deleteProduct")
  public void createProduct(UUID id) {
    ps.delete(id);
  }

}
