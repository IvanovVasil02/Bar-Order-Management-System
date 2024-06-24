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
import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessageService;
import IvanovVasil.OrderManagmentSystem.webSocket.ElementToUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductsService {

  @Autowired
  ProductsRepository pr;

  @Autowired
  ChatMessageService cms;

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
    Product product;

    if (ProductCategory.valueOf(body.productCategory()) == ProductCategory.HOT_DISHES) {
      product = new HotDishes(
              body.productName(),
              Double.parseDouble(String.valueOf(body.price())),
              HotDishesCategory.valueOf(body.productSubCategory()),
              body.ingredientList()
      );
    } else if (ProductCategory.valueOf(body.productCategory()) == ProductCategory.DRINK) {
      product = new Drink(
              body.productName(),
              Double.parseDouble(String.valueOf(body.price())),
              DrinkCategory.valueOf(body.productSubCategory()),
              (long) Integer.parseInt(String.valueOf(body.quantity()))
      );
    } else {
      throw new IllegalArgumentException("Invalid product category");
    }

    pr.save(product);
    cms.sendUpdateMessage(ElementToUp.PRODUCT);
    return product;
  }

  public Product editProduct(UUID productId, ProductDTO body) {
    Product product = findById(productId);
    if (product.getProductCategory() == ProductCategory.HOT_DISHES) {
      HotDishes hotDish = (HotDishes) product;

      if (!body.productCategory().isEmpty()) {
        hotDish.setProductCategory(ProductCategory.valueOf(body.productCategory()));
      }

      if (!body.productSubCategory().isEmpty()) {
        hotDish.setSubCategory(HotDishesCategory.valueOf(body.productCategory()));
      }

      if (!body.productName().isEmpty()) {
        hotDish.setName(body.productName());
      }

      if (!body.price().isBlank()) {
        hotDish.setPrice(Double.valueOf(body.price()));
      }

      if (!body.ingredientList().isEmpty()) {
        hotDish.setIngredients(body.ingredientList());
      }
    }

    if (product.getProductCategory() == ProductCategory.DRINK) {
      Drink drink = (Drink) product;

      if (!body.productCategory().isEmpty()) {
        drink.setProductCategory(ProductCategory.valueOf(body.productCategory()));
      }

      if (!body.productSubCategory().isEmpty()) {
        drink.setSubCategory(DrinkCategory.valueOf(body.productCategory()));
      }

      if (!body.productName().isEmpty()) {
        drink.setName(body.productName());
      }

      if (!body.price().isBlank()) {
        drink.setPrice(Double.valueOf(body.price()));
      }

      if (body.quantity() != null) {
        drink.setQuantity(Long.valueOf(body.quantity()));
      }
    }

    pr.save(product);
    cms.sendUpdateMessage(ElementToUp.PRODUCT);
    return product;
  }


  protected void delete(UUID id) {
    pr.deleteById(id);
    cms.sendUpdateMessage(ElementToUp.PRODUCT);
  }
}
