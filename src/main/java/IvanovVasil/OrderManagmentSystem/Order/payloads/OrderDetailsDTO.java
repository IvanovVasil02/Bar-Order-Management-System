package IvanovVasil.OrderManagmentSystem.Order.payloads;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderDetailsDTO(
        UUID id,
        Long quantity,
        String note
) {
}
