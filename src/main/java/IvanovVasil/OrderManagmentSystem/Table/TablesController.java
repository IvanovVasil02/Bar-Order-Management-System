package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.exceptions.BadRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {

  @Autowired
  TablesService ts;

  @GetMapping("")
  public List<TableResultDTO> getTables() {
    return ts.getAllTables();
  }

  @PostMapping("/addTables/{num}")
  public List<TableResultDTO> createTables(@PathVariable("num") int num) {
    return ts.createTables(num);
  }

  @DeleteMapping("/{tableNumber}")
  public void deleteTable(@PathVariable Long tableNumber) {
    ts.deleteTableByTableNumber(tableNumber);
  }
}
