package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record IngredientDTO(
        @NotBlank(message = "ingredientName cannot be blank")
        @Size(min = 2, max = 30, message = "ingredientName must be between 2 and 30 characters")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "ingredientName must contain only letters")
        String ingredientName,

        @ValidEnum(enumClass = IngredientCategory.class, message = "Invalid ingredient category!")
        String ingredientCategory
) {
}
