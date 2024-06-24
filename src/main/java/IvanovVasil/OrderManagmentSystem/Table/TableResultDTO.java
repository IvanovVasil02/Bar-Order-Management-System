package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Order.payloads.OrderResultDTO;
import IvanovVasil.OrderManagmentSystem.validation.enumsValidator.ValidEnum;
import IvanovVasil.OrderManagmentSystem.validation.uuidsValidator.ValidUUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TableResultDTO(
        UUID table_id,
        Long tableNumber,
        TableState tableState,
        OrderResultDTO order) {
}
