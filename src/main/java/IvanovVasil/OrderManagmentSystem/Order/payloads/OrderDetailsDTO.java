package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.validation.longValidator.ValidLong;
import IvanovVasil.OrderManagmentSystem.validation.uuidsValidator.ValidUUID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderDetailsDTO(
        @NotNull(message = "Product id cannot be null")
        @ValidUUID(message = "Product id not valid")
        UUID id,

        @NotNull(message = "Order detail quantity cannot be null")
        @ValidLong(message = "Entered quantity is not a valid number")
        Long quantity,

        String note
) {
}
