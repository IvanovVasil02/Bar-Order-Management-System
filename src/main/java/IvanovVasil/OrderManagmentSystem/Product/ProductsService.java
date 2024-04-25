package IvanovVasil.OrderManagmentSystem.Product;

import IvanovVasil.OrderManagmentSystem.Product.entities.Drink;
import IvanovVasil.OrderManagmentSystem.Product.entities.HotDishes;
import IvanovVasil.OrderManagmentSystem.Product.entities.Product;
import IvanovVasil.OrderManagmentSystem.Product.enums.DrinkCategory;
import IvanovVasil.OrderManagmentSystem.Product.enums.HotDishesCategory;
import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.Product.interfaces.ProductsRepository;
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

  public Product save(Product product) {
    return pr.save(product);
  }

  public Product findById(UUID id) {
    return pr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Product> getAllProducts() {
    return pr.findAll();
  }

  public Product createProduct(ProductDTO body) {
    Product product = null;

    if (body.productCategory() == ProductCategory.HOT_DISHES) {
      product = new HotDishes(
              body.name(),
              body.price(),
              (HotDishesCategory) body.productSubcategory(),
              body.ingredients()
      );

    } else if (body.productCategory() == ProductCategory.DRINK) {
      product = new Drink(
              body.name(),
              body.price(),
              (DrinkCategory) body.productSubcategory(),
              body.quantity()
      );
    }
    if (product != null) {
      return pr.save(product);
    } else {
      throw new IllegalArgumentException("Invalid product category");
    }
  }
  

  protected void delete(UUID id) {
    pr.deleteById(id);
  }
}
