package com.matuga.ai.a4_multimodal.image;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/multi-modal")
public class ImageDetection {

  @Value("classpath:/images/japan-background-digital-art.jpg")
  Resource sampleImage;

  private final ChatClient chatClient;

  public ImageDetection(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping(path = "/image-to-text")
  public String imageToText() {
    return chatClient
        .prompt()
        .user(
            u -> {
              u.text("Can you describe what you see/compute in the following image ?");
              u.media(MimeTypeUtils.IMAGE_JPEG, sampleImage);
            })
        .call()
        .content();
  }
}
