package com.matuga.ai.a8_tools.b1_dateTime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/tools/date-time")
public class DateTimeChatController {
  private final ChatClient chatClient;

  public DateTimeChatController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping("/date")
  public String tools() {
    /*
      // again due to limitation of gemini-api we can use tools directly but below code will reproduce same results
      return chatClient
            .prompt("What day is tomorrow?")
            .tools(new DateTimeTools())
            .call()
            .content();
    */
    String currentDate =
        LocalDateTime.now()
            .atZone(LocaleContextHolder.getTimeZone().toZoneId())
            .toLocalDate()
            .toString();

    return chatClient
        .prompt("What day is tomorrow if today is " + currentDate + "?")
        .call()
        .content();
  }

  @GetMapping("/date-tool")
  public String getDate() {
    // again due to limitation of gemini-api we can use tools directly but below code will reproduce
    // same results
    return chatClient.prompt("What day is tomorrow?").tools(new DateTimeTools()).call().content();
  }
}