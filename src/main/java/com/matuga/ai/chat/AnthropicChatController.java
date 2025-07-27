package com.matuga.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AnthropicChatController {

  private final ChatClient chatClient;

  public AnthropicChatController(@Qualifier("anthropicChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  @GetMapping("/chat-an")
  public String chatAnthropic() {
    return chatClient
        .prompt() // Start building a prompt
        .user("tell me fact in one liner about java") // Set user input for the LLM
        .call() // Blocking call to fetch the result
        .content(); // Extract the LLM's response text
  }


  @GetMapping("/stream-an")
  public Flux<String> streamAnthropic() {
    return chatClient
        .prompt() // Start building the prompt
        .user("I'm in Delhi, tell me 5 places I should visit") // Set the user prompt
        .stream() // Initiates a streaming request
        .content(); // Returns a Flux<String> with streamed tokens or lines
  }

  @GetMapping("/joke-an")
  public ChatResponse jokeAnthropic() {
    return chatClient
        .prompt() // Start building the prompt
        .user("tell me a software-engineer joke") // Set the user prompt
        .call() // Blocking call to fetch the result
        .chatResponse(); // Returns response with metaData.
  }
}
