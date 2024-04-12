package IvanovVasil.OrderManagmentSystem.Table;

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
  public List<Table> getTables() {
    return ts.getAllTables();
  }

  @PostMapping("/addTable")
  public Table createTable() {
    return ts.createTable();
  }

  @DeleteMapping("")
  public void deleteTable(@RequestParam UUID id) {
    ts.deleteTableById(id);
  }
}
