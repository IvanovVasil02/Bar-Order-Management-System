package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.Order.OrderDetails;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderDTO(
        UUID tableId,
        LocalDateTime localDateTime,
        String note,
        List<OrderDetails> productList) {
}
