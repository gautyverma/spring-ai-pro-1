package com.matuga.ai.a5_memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/memory")
public class MemoryController {

  private final ChatClient chatClient;

  public MemoryController(ChatClient.Builder builder, ChatMemory chatMemory) {
    this.chatClient =
        builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
  }

  @GetMapping(path = "/conversation")
  public String conversation(
      @RequestParam(value = "sentence", defaultValue = "How you doing ?") String sentence) {
    return chatClient.prompt().user(sentence).call().content();
  }
}
