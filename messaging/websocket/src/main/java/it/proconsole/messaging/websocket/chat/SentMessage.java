package it.proconsole.messaging.websocket.chat;

import java.time.LocalDateTime;

public record SentMessage(
        LocalDateTime time,
        String from,
        String text
) {
  public static SentMessage from(Message message) {
    return new SentMessage(
            LocalDateTime.now(),
            message.from(),
            message.text()
    );
  }
}
