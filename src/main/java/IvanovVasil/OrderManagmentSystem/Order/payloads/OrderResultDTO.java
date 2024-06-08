package IvanovVasil.OrderManagmentSystem.Order.payloads;

import IvanovVasil.OrderManagmentSystem.Order.enums.OrderState;
import IvanovVasil.OrderManagmentSystem.Table.TableState;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResultDTO(
        UUID order_id,
        UUID table_id,
        Long tableNumber,
        List<OrderDetailsResultDTO> productList,
        OrderState orderState,
        TableState tableState,
        Double totalPrice,
        Double remainingToPay,
        LocalDateTime dateTime) {
}
