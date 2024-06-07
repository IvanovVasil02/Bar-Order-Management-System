package IvanovVasil.OrderManagmentSystem.Order.payloads;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderDetailsResultDTO(
        UUID id,
        String name,
        Long quantity,
        Long preparedQuantity,
        Long paidQuantity,
        double price,
        UUID orderId,
        UUID tableId,
        Long tableNumber,
        Double subtotal,
        LocalDateTime localDateTime

) {
}
