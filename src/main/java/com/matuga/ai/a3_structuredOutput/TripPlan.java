package com.matuga.ai.a3_structuredOutput;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/trip")
public class TripPlan {

  private final ChatClient chatClient;

  public TripPlan(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping(path = "/plan/unstructured")
  public String planUnstructured(
      @RequestParam(value = "place", defaultValue = "kasol") String place) {
    return chatClient
        .prompt()
        .user(
            plan -> {
              plan.text("Things to do in {place}");
              plan.param("place", place);
            })
        .call()
        .content();
  }

  @GetMapping(path = "/plan/structured")
  public Itinerary planStructured(
      @RequestParam(value = "place", defaultValue = "kasol") String place) {
    return chatClient
        .prompt()
        .user(
            plan -> {
              plan.text("Things to do in {place}");
              plan.param("place", place);
            })
        .call()
        .entity(Itinerary.class);
  }
}
