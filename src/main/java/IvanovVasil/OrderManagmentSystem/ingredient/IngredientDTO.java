package IvanovVasil.OrderManagmentSystem.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record IngredientDTO(
        @NotBlank
        @Size(min = 2, max = 30)
        @Pattern(regexp = "^[a-zA-Z]+$", message = "ingredientName must contain only letters")
        String ingredientName,
        @NotBlank
        @Size(min = 2, max = 30)
        @Pattern(regexp = "^[a-zA-Z]+$", message = "ingredientCategory must contain only letters")
        String ingredientCategory) {
}
