package com.YouTubeTools.service;

import com.YouTubeTools.model.SearchVideo;
import com.YouTubeTools.model.Video;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class YouTubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.api.search.url}")
    private String searchUrl;

    @Value("${youtube.api.max.related.videos}")
    private int maxRelatedVideos;

    public SearchVideo searchVideos(String videoTitle) {
        RestTemplate restTemplate = new RestTemplate();

        // 🔹 Step 1: Call YouTube Search API for the primary video
        String primaryUrl = UriComponentsBuilder.fromHttpUrl(searchUrl)
                .queryParam("part", "snippet")
                .queryParam("q", videoTitle)
                .queryParam("type", "video")
                .queryParam("maxResults", 1)
                .queryParam("key", apiKey)
                .toUriString();

        Map<String, Object> primaryResponse = restTemplate.getForObject(primaryUrl, Map.class);

        SearchVideo result = new SearchVideo();

        if (primaryResponse != null && primaryResponse.containsKey("items")) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) primaryResponse.get("items");
            if (!items.isEmpty()) {
                Map<String, Object> item = items.get(0);
                Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");

                Video primary = new Video((String) snippet.get("title"));
                primary.setChannelTitle((String) snippet.get("channelTitle"));
                primary.setTags(List.of(videoTitle)); // 👈 you can improve by calling Videos API to get real tags
                result.setPrimaryVideo(primary);
            }
        }

        // 🔹 Step 2: Call YouTube Search API for related videos
        String relatedUrl = UriComponentsBuilder.fromHttpUrl(searchUrl)
                .queryParam("part", "snippet")
                .queryParam("q", videoTitle)
                .queryParam("type", "video")
                .queryParam("maxResults", maxRelatedVideos)
                .queryParam("key", apiKey)
                .toUriString();

        Map<String, Object> relatedResponse = restTemplate.getForObject(relatedUrl, Map.class);

        List<Video> relatedVideos = new ArrayList<>();
        if (relatedResponse != null && relatedResponse.containsKey("items")) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) relatedResponse.get("items");
            for (Map<String, Object> item : items) {
                Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                Video v = new Video((String) snippet.get("title"));
                v.setChannelTitle((String) snippet.get("channelTitle"));
                v.setTags(List.of(videoTitle, "related")); // 👈 placeholder tags
                relatedVideos.add(v);
            }
        }

        result.setRelatedVideos(relatedVideos);

        return result;
    }
}
