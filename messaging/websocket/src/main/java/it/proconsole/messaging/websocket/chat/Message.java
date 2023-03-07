package it.proconsole.messaging.websocket.chat;

public record Message(
        String from,
        String text
) {

}
