package IvanovVasil.OrderManagmentSystem.exceptions.ExceptionPayloads;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
