package IvanovVasil.OrderManagmentSystem.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void sendUpdateMessage(ElementToUp message) {
    ChatMessage chatMessage = ChatMessage.builder()
            .elementToUp(message)
            .sender("Server")
            .type(MessageType.CHAT)
            .build();
    messagingTemplate.convertAndSend("/topic/public", chatMessage);
  }
}
