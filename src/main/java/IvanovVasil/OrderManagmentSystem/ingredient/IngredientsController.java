package IvanovVasil.OrderManagmentSystem.ingredient;

import IvanovVasil.OrderManagmentSystem.webSocket.ChatMessage;
import IvanovVasil.OrderManagmentSystem.webSocket.MessageType;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
  @Autowired
  IngredientsService is;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @GetMapping("")
  public List<Ingredient> getAll() {
    return is.getAllIngredients();
  }

  @GetMapping("/{category}")
  public List<Ingredient> getAllIngredientsByCategory(@PathParam("category") String category) {
    return is.getAllIngredientsByCategory();
  }

  @PostMapping("")
  public Ingredient addIngredient(@RequestBody IngredientDTO body) {
    sendUpdateMessage("Ingredient added: " + body);
    return is.save(body);
  }

  @PutMapping("")
  public Ingredient editIngredient(@RequestParam UUID ingredientId, @RequestBody IngredientDTO body) {
    return is.editIngredient(ingredientId, body);
  }

  @DeleteMapping("")
  public void deleteIngredient(@RequestParam UUID ingredientId) {
    is.delete(ingredientId);
  }

  private void sendUpdateMessage(String message) {
    ChatMessage chatMessage = ChatMessage.builder()
            .content(message)
            .sender("Server")
            .type(MessageType.CHAT)
            .build();
    messagingTemplate.convertAndSend("/topic/public", chatMessage);
  }
}
