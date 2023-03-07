package it.proconsole.messaging.websocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatResource {
  private final List<SentMessage> messages = new ArrayList<>();

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public SentMessage send(Message message) {
    var sentMessage = SentMessage.from(message);
    messages.add(sentMessage);
    return sentMessage;
  }

  @SubscribeMapping("/topic/messages")
  public List<SentMessage> initialReply() {
    return messages;
  }
}
