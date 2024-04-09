package IvanovVasil.OrderManagmentSystem.Order.payloads;

import lombok.Builder;

@Builder
public record OrderDetailsDTO(
        String id,
        Long quantity,
        String note
) {
}
