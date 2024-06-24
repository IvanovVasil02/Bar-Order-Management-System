package IvanovVasil.OrderManagmentSystem.Product.payloads;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.validation.doubleValidator.ValidDouble;
import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.ValidEnum;
import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import IvanovVasil.OrderManagmentSystem.validation.longValidator.ValidLong;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDTO(
        @ValidEnum(enumClass = ProductCategory.class, message = "The product category entered is not valid")
        String productCategory,

        @NotNull(message = "The product subcategory entered is not valid")
        String productSubCategory,

        @NotBlank(message = "Product name cannot be blank")
        @Size(min = 2, max = 30, message = "product name must be between 2 and 30 characters")
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "product name must contain only letters")
        String productName,

        @ValidDouble(message = "The price field must be a valid decimal number")
        String price,

        @ValidLong(message = "The quantity must be a valid number")
        String quantity,

        List<Ingredient> ingredientList
) {
}
