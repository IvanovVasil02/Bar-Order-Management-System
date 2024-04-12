package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.Order.entities.OrderDetails;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderResultDTO(
        UUID order_id,
        UUID table_id,
        Long tableNumber,
        List<OrderDetailsResultDTO> productList,
        Double totalPrice) {
}
