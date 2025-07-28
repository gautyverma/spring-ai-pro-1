package com.matuga.ai.a4_multimodal;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.model.image.observation.autoconfigure.ImageObservationProperties;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/multi-modal")
public class ImageGeneration {
  String model = "gemini-2.0-flash-preview-image-generation";

  private final OpenAiImageModel imageModel;

  public ImageGeneration(OpenAiImageModel imageModel) {
    this.imageModel = imageModel;
  }

  @GetMapping(path = "/generate-image")
  public ResponseEntity<Map<String, String>> generateImage(
      @RequestParam(value = "prompt", defaultValue = "kasol mountains") String prompt) {

    ImageOptions options =
        OpenAiImageOptions.builder()
            .model(model)
            .width(1024)
            .height(1024)
            .quality("hd")
            .style("natural")
            .build();

    ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
    ImageResponse imageResponse = imageModel.call(imagePrompt);

    String url = imageResponse.getResult().getOutput().getUrl();
    return ResponseEntity.ok(Map.of("prompt", prompt, "imageUrl", url));
  }
}
