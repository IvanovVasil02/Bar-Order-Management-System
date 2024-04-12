package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TablesSerice {

  @Autowired
  TablesRepository tr;

  public Table save(Table table) {
    return tr.save(table);
  }

  public Table findById(UUID id) {
    return tr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Table> getAllTables() {
    return tr.findAll();
  }

  public Table createTable() {
    Table table = new Table();
    return tr.save(table);
  }

  private void delete(Table table) {
    tr.deleteById(table.getId());
  }

  public void deleteTableById(UUID id) {
    tr.deleteById(id);
  }
}
