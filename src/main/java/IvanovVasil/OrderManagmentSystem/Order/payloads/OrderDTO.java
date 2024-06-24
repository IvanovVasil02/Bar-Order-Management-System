package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.validation.uuidsValidator.ValidUUID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderDTO(
        @NotNull(message = "Table id cannot be null")
        @ValidUUID(message = "Table id not valid")
        UUID tableId,

        String note,

        @NotNull(message = "Order cannot be null")
        List<OrderDetailsDTO> productList) {
}
