package com.matuga.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OpenAiChatController {

  private final ChatClient chatClient;

  public OpenAiChatController(@Qualifier("openAIChatClient") ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  /**
   * Endpoint: GET /chat Description: Makes a one-time synchronous call to the LLM and returns a
   * single-line fact about Java. This is a blocking call and will wait for the LLM to respond
   * before returning.
   */
  @GetMapping("/chat")
  public String chat() {
    return chatClient
        .prompt() // Start building a prompt
        .user("tell me fact in one liner about java") // Set user input for the LLM
        .call() // Blocking call to fetch the result
        .content(); // Extract the LLM's response text
  }

  /**
   * Endpoint: GET /stream Description: Makes a streaming (non-blocking) call to the LLM. The LLM
   * will stream its response back in chunks (like 5 places to visit in Delhi). This uses Project
   * Reactor's Flux to return data reactively.
   *
   * <p>to Run using Git-bash --> curl http://localhost:8080/stream
   */
  @GetMapping("/stream")
  public Flux<String> stream() {
    return chatClient
        .prompt() // Start building the prompt
        .user("I'm in Delhi, tell me 5 places I should visit") // Set the user prompt
        .stream() // Initiates a streaming request
        .content(); // Returns a Flux<String> with streamed tokens or lines
  }

  @GetMapping("/joke")
  public ChatResponse joke() {
    return chatClient
        .prompt() // Start building the prompt
        .user("tell me a software-engineer joke") // Set the user prompt
        .call() // Blocking call to fetch the result
        .chatResponse(); // Returns response with metaData.
  }
}
