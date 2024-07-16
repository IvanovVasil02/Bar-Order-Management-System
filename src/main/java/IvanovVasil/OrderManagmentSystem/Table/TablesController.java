package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.User.User;
import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {

  @Autowired
  TablesService ts;

  @GetMapping("")
  public List<TableResultDTO> getTables(@AuthenticationPrincipal User user) {
    return ts.getAllTables(user);
  }

  @PostMapping("/addTables/{num}")
  public List<TableResultDTO> createTables(@PathVariable("num") int num, @AuthenticationPrincipal User user) {
    return ts.createTables(num, user);
  }

  @DeleteMapping("/{tableNumber}")
  public void deleteTable(@PathVariable Long tableNumber, @AuthenticationPrincipal User user) {
    ts.deleteTableByTableNumber(tableNumber, user);
  }
}
