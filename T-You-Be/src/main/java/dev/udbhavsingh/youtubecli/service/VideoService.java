package dev.udbhavsingh.youtubecli.service;

import dev.udbhavsingh.youtubecli.client.YoutubeDataClient;
import dev.udbhavsingh.youtubecli.config.YouTubeConfigProps;
import dev.udbhavsingh.youtubecli.model.SearchListResponse;
import dev.udbhavsingh.youtubecli.model.Video;
import dev.udbhavsingh.youtubecli.model.VideoListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);
    private final List<Video> videos = new ArrayList<>();
    private final YoutubeDataClient client;
    private final YouTubeConfigProps youtubeConfig;

    public VideoService(YoutubeDataClient client, YouTubeConfigProps youtubeConfig) {
        this.client = client;
        this.youtubeConfig = youtubeConfig;
        this.loadAllVideosThisYear("", LocalDate.now().getYear());
    }

    private void loadAllVideosThisYear(String pageToken, int year) {
        SearchListResponse search = client.searchByPublishedAfter(youtubeConfig.channelId(), youtubeConfig.key(), 50, pageToken, year + "-01-01T00:00:00Z");
        search.items().stream()
                .filter(result -> result.id().kind().equals("youtube#video"))
                .map(result -> client.getVideo(result.id().videoId(), youtubeConfig.key()))
                .map(VideoListResponse::items)
                .forEach(videos::addAll);

        if(search.nextPageToken() != null && !search.nextPageToken().isEmpty()) {
            loadAllVideosThisYear(search.nextPageToken(), year);
        }
    }

    public List<Video> findRecent(Integer max) {
        return videos.stream().limit(max).toList();
    }

    public List<Video> findAllByYear(Integer year) {
        return videos.stream().filter(v -> v.snippet().publishedAt().getYear() == year).toList();
    }
}
