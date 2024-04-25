package IvanovVasil.OrderManagmentSystem.Order.payloads;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderDetailsResultDTO(
        UUID productId,
        String productName,
        String productDescription,
        Long quantity,
        Long preparedQuantity,
        Long paidQuantity,
        double productPrice,
        UUID orderId,
        UUID tableId,
        Long tableNumber,
        Double subtotal,
        LocalDateTime localDateTime

) {
}
