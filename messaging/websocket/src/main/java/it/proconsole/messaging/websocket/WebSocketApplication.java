package it.proconsole.messaging.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebSocketApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebSocketApplication.class, args);
  }
}
