package com.example.spring.ai.controller;

import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")  // ✅ Added base mapping
public class AIController {

    private final OpenAiImageModel openAiImageModel;

    public AIController(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "AI Controller is working!";
    }

    @GetMapping("/image/{prompt}")  // ✅ Corrected endpoint path
    public ImageGeneration generateImage(@PathVariable("prompt") String prompt) {
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(prompt, OpenAiImageOptions.builder()
                        .withHeight(1024)
                        .withWidth(1024)
                        .withQuality("hd")
                        .withN(1)  // ✅ Number of images
                        .build())
        );

        // ✅ Extract image URL correctly
        List<ImageGeneration> imageUrls = response.getResults();
            return imageUrls.get(0);  // ✅ Extract first image URL

    }
}
