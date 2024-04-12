package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.Order.entities.Order;
import IvanovVasil.OrderManagmentSystem.Product.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderDetailsResultDTO(
        UUID productId,
        String productName,
        String productDescription,
        Long quantity,
        Long paidQuantity,
        double productPrice,
        UUID orderId,
        UUID tableId,
        Long tableNumber,
        Double subtotal,
        LocalDateTime localDateTime

) {
}
