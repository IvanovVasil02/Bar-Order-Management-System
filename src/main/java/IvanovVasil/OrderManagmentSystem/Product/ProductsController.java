package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.entities.HotDishes;
import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import IvanovVasil.OrderManagmentSystem.Product.enums.HotDishesCategory;
import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

  @PostMapping("")
  public void createProduct(@RequestBody ProductDTO body) {

    if (Arrays.stream(HotDishesCategory.values()).anyMatch(category -> category.name().equals(body.productSubCategory()))) {
      System.out.println(" è presente");
    } else {
      System.out.println("non è presente ");
    }


    ps.createProduct(body);
  }

  @PutMapping("")
  public Product editProduct(@RequestParam UUID productId, @RequestBody ProductDTO body) {
    return ps.editProduct(productId, body);
  }

  @DeleteMapping("")
  public void createProduct(UUID id) {
    ps.delete(id);
  }

}
