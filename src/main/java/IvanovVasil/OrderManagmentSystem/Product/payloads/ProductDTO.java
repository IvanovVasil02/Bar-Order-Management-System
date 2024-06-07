package IvanovVasil.OrderManagmentSystem.Product.payloads;

import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO(
        String productCategory,
        String productSubCategory,
        String productName,
        Double price,
        Long quantity,
        List<Ingredient> ingredientList
) {
}
