/*
package com.matuga.ai.config;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatModelConfig {

  @Bean
  @Primary
  public ChatClient.Builder openAIChatClientBuilder(OpenAiChatModel model) {
    return ChatClient.builder(model);
  }

  @Bean
  public ChatClient anthropicChatClient(AnthropicChatModel chatModel) {
    return ChatClient.create(chatModel);
  }
}
*/
