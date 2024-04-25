package IvanovVasil.OrderManagmentSystem.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, UUID> {


  Ingredient findByNameIgnoreCase(String ingredient);
}
