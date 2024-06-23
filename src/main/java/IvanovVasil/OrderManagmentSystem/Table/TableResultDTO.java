package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TableResultDTO(UUID table_id, Long tableNumber, TableState tableState, OrderResultDTO order) {
}
