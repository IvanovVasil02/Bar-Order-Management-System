package IvanovVasil.OrderManagmentSystem.Table;

import IvanovVasil.OrderManagmentSystem.Product.Product;
import IvanovVasil.OrderManagmentSystem.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class TablesSerice {

  @Autowired
  TablesRepository tr;

  public Table save (Table table){
    return tr.save(table);
  }

  public Table findById(UUID id) {
    return tr.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Table> getAllMedicines() {
    return tr.findAll();
  }

  private Table createProduct(String name, String description, Double price, Long quantity){
    Table table = new Table();
    return tr.save(table);
  }

  private void delete (Table table){
    tr.deleteById(table.getId());
  }
}
