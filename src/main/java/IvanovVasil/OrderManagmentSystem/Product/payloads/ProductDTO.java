package IvanovVasil.OrderManagmentSystem.Product.payloads;

import lombok.Builder;

@Builder
public record ProductDTO(
        String name, String description, Double price, Long quantity
) {
}
