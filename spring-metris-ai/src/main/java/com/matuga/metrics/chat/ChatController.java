package com.matuga.metrics.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String chat(@RequestParam(defaultValue = "Who is Modi?") String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
