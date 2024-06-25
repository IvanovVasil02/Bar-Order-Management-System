package IvanovVasil.OrderManagmentSystem.Order.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderDetailsDTO(
        @NotNull(message = "Product id cannot be null")
        UUID id,

        @NotNull(message = "Order detail quantity cannot be null")
        @Min(value = 1, message = "The minimum quantity that can be entered is 1")
        Long quantity,

        String note
) {
}