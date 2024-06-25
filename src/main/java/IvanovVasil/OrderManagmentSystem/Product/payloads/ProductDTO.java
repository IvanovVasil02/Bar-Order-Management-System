package IvanovVasil.OrderManagmentSystem.Product.payloads;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.ValidEnum;
import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO(
        @ValidEnum(enumClass = ProductCategory.class, message = "The product category entered is not valid")
        String productCategory,

        @NotNull(message = "The product subcategory entered is not valid")
        String productSubCategory,


        @Size(min = 2, max = 30, message = "product name must be between 2 and 30 characters")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "product name must contain only letters")
        String productName,

        @NotNull(message = "Price cannot be null")
        @Digits(integer = 10, fraction = 2, message = "Price must be a valid number with up to 2 decimal places")
        Double price,

        Long quantity,

        List<Ingredient> ingredientList
) {
}
