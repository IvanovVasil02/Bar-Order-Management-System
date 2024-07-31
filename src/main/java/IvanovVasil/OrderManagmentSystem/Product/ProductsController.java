package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import IvanovVasil.OrderManagmentSystem.Product.payloads.ProductDTO;
import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsController {

  @Autowired
  ProductsService ps;

  @GetMapping("")
  public List<Product> getAllProducts(@AuthenticationPrincipal User user) {
    return ps.getAllProducts(user);
  }

  @PostMapping("")
  public Product createProduct(@Valid @RequestBody ProductDTO body, BindingResult validation, @AuthenticationPrincipal User user) {

    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the entered product data!", validation.getAllErrors());
    } else {
      return ps.createProduct(body, user);
    }
  }

  @PutMapping("")
  public Product editProduct(@RequestParam UUID productId, @Valid @RequestBody ProductDTO body, BindingResult validation, @AuthenticationPrincipal User user) {
    if (validation.hasErrors()) {
      throw new BadRequestException("There was an issue with the entered product data!");
    } else {
      return ps.editProduct(productId, body, user);
    }
  }

  @DeleteMapping("")
  public void deleteProduct(UUID id, @AuthenticationPrincipal User user) {
    ps.delete(id, user);
  }

}
