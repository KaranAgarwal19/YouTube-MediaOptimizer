package com.YouTubeTools.controller;

import com.YouTubeTools.model.SearchVideo;
import com.YouTubeTools.service.YouTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/youtube")
public class YouTubeTagsController {

    @Autowired
    private YouTubeService youTubeService;

    @Value("${youtube.api.key}")
    private String apiKey;

    private boolean isApiKeyConfigured() {
        return apiKey != null && !apiKey.isEmpty();
    }

    @PostMapping("/search") // ✅ fixed typo
    public String videoTags(@RequestParam("videoTitle") String videoTitle, Model model) {

        // Validate API key
        if (!isApiKeyConfigured()) {
            model.addAttribute("error", "API key is not configured");
            return "home";
        }

        // Validate input
        if (videoTitle == null || videoTitle.trim().isEmpty()) {
            model.addAttribute("error", "Video title is required");
            return "home";
        }

        try {
            SearchVideo result = youTubeService.searchVideos(videoTitle);
            model.addAttribute("primaryVideo", result.getPrimaryVideo());
            model.addAttribute("relatedVideos", result.getRelatedVideos());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "home"; // ✅ single return point
    }
}
