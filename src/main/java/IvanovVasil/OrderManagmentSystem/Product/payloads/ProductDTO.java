package IvanovVasil.OrderManagmentSystem.Product.payloads;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.Product.interfaces.Subcategory;
import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO(
        ProductCategory productCategory,
        Subcategory productSubcategory,
        String name,
        Double price,
        Long quantity,
        List<Ingredient> ingredients
) {
}
