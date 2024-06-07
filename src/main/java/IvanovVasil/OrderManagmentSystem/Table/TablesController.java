package IvanovVasil.OrderManagmentSystem.Table;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
  public List<Table> createTables(@PathVariable int num) {
    return ts.createTables(num);
  }

  @DeleteMapping("/{tableNumber}")
  public void deleteTable(@PathVariable Long tableNumber) {
    ts.deleteTableByTableNumber(tableNumber);
  }
}
