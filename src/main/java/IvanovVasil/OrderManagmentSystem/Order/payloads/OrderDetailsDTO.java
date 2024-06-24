package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.validation.uuidsValidator.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderDetailsDTO(
        @NotNull(message = "Order id cannot be null")
        @ValidUUID(message = "Order id not valid")
        UUID id,

        @NotNull(message = "Order detail id cannot be null")
        @Pattern(regexp = "^-?\\d+$", message = "Order detail quantiy must be an integer")
        Long quantity,

        String note
) {
}
