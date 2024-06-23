package IvanovVasil.OrderManagmentSystem.Order.payloads;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderDTO(
        UUID tableId,
        String note,
        List<OrderDetailsDTO> productList) {
}
