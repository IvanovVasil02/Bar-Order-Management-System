package IvanovVasil.OrderManagmentSystem.Product.payloads;

import IvanovVasil.OrderManagmentSystem.Product.enums.ProductCategory;
import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.ValidEnum;
import IvanovVasil.OrderManagmentSystem.ingredient.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
        @Pattern(regexp = "^[a-zA-Z]+$", message = "product name must contain only letters")
        String productName,

        @NotNull(message = "The price cannot be null")
        @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "The price must be a valid number")
        Double price,

        @NotNull(message = "Order detail id cannot be null")
        @Pattern(regexp = "^-?\\d+$", message = "Order detail quantiy must be an integer")
        Long quantity,

        List<Ingredient> ingredientList
) {
}
