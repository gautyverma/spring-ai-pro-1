package com.matuga.ai.a2_prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/we-bank/")
public class WeBankController {
  private final ChatClient chatClient;

  public WeBankController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping(path = "/chat")
  public String chat(@RequestParam String message) {
    var systemInstruction =
"""
    You are a customer service assitant for we-bank.
    You can only discuss:
    - Account balance and transactions.
    - Branch locations and hours.
    - General banking services.

   If asked anything else, respond: "I can only help with banking-related queries."
""";
    return chatClient.prompt().user(message).system(systemInstruction).call().content();
  }
}
