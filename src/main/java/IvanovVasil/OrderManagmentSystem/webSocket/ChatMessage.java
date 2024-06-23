package IvanovVasil.OrderManagmentSystem.webSocket;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
  private ElementToUp elementToUp;
  private String sender;
  private MessageType type;
}
