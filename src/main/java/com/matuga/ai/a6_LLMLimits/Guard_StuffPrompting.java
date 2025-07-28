package com.matuga.ai.a6_LLMLimits;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/llm")
public class Guard_StuffPrompting {

  private final ChatClient chatClient;

  public Guard_StuffPrompting(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  /*
   * How can we improve this?
   * We could send the context along with the request, but we would have to do that every single time.
   *   - we are paying for those tokens even when the question has nothing do with that
   */
  @GetMapping("/models")
  public String models() {
    return chatClient
        .prompt()
        .user(
            "Can you give me an up to date list of popular large language models and their current context window size")
        .call()
        .content();
  }

  // I would also add a system prompt for this endpoint to note that this should only be used for
  // researching models and their context window size
  @GetMapping("/models/stuff-the-prompt")
  public String modelsWithData() {
    var system =
        """
                If you're asked about up to date language models and there context window here is some information to help you with your response:
                [
                  { "company": "OpenAI",        "model": "GPT-4o",                 "context_window_size": 128000 },
                  { "company": "OpenAI",        "model": "o1-preview",             "context_window_size": 128000 },

                  { "company": "Anthropic",     "model": "Claude Opus 4",          "context_window_size": 200000 },
                  { "company": "Anthropic",     "model": "Claude Sonnet 4",        "context_window_size": 200000 },

                  { "company": "Google",        "model": "Gemini 2.5 Pro",         "context_window_size": 1000000 },
                  { "company": "Google",        "model": "Gemini 2.0 Pro (Exp.)",  "context_window_size": 2000000 },
                  { "company": "Google",        "model": "Gemini 2.0 Flash",       "context_window_size": 1000000 },

                  { "company": "Meta AI",       "model": "Llama 3.1 405B",         "context_window_size": 128000 },

                  { "company": "xAI",           "model": "Grok 3",                 "context_window_size": 1000000 },

                  { "company": "Mistral AI",    "model": "Mistral Large 2",        "context_window_size": 128000 },

                  { "company": "Alibaba Cloud", "model": "Qwen 2.5 72B",           "context_window_size": 128000 },

                  { "company": "DeepSeek",      "model": "DeepSeek R1",            "context_window_size": 128000 }
                ]
                """;
    return chatClient
        .prompt()
        .user(
            "Can you give me an up to date list of popular large language models and their current context window size")
        .system(system)
        .call()
        .content();
  }

  @GetMapping(path = "/prompt-guarding")
  public String bankPromptGuarding(@RequestParam String message) {
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
