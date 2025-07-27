package com.matuga.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "facts/")
public class FactsController {

  private final ChatClient chatClient;

  public FactsController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping(path = "topic/{topicName}")
  public String getFactTopic(@PathVariable("topicName") String topicName) {
    var systemInstruction =
"""
Keep it Short & Precise:
Output should be a single sentence, ideally under 25 words.

Fact-Only Content:
Avoid opinions, speculations, or emotional language. Share only objective, widely accepted or scientifically verifiable facts.

One Fact per Line:
If multiple facts are requested, format them as separate lines, each with a unique, standalone fact.

Relevance First:
Focus on facts that are most directly related to the topic. Avoid unrelated trivia.

No Redundancy:
Do not repeat or rephrase the same point. Each fact must add new information.

Avoid Source Names (unless critical):
Do not mention organizations or studies unless the source itself is the fact (e.g., “NASA found…”).

Proper Grammar & Tone:
Use clear, professional language with correct grammar and punctuation.
""";
    return chatClient
        .prompt()
        .user(
            prompt -> {
              prompt.text("tell some thing about {objectName}");
              prompt.param("objectName", topicName);
            })
        .system(systemInstruction)
        .call()
        .content();
  }
}
