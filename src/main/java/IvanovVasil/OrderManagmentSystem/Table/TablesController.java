package IvanovVasil.OrderManagmentSystem.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TablesController {

  @Autowired
  TablesSerice ts;

  @GetMapping("")
  public List<TableResultDTO> getTables() {
    return ts.getAllTables();
  }

  @PostMapping("/addTables/{num}")
  public List<RestaurantTable> createTables(@PathVariable int num) {
    return ts.createTables(num);
  }

  @DeleteMapping("/{tableNumber}")
  public void deleteTable(@PathVariable Long tableNumber) {
    ts.deleteTableByTableNumber(tableNumber);
  }
}
