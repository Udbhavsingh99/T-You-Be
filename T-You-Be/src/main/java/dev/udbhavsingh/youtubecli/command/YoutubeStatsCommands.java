package dev.udbhavsingh.youtubecli.command;

import dev.udbhavsingh.youtubecli.model.TeamTabRow;
import dev.udbhavsingh.youtubecli.model.Video;
import dev.udbhavsingh.youtubecli.service.CommandService;
import dev.udbhavsingh.youtubecli.service.ReportService;
import dev.udbhavsingh.youtubecli.service.VideoService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.TableBuilder;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent("Youtube Stats Commands")
public class YoutubeStatsCommands {

    private final CommandService commandService;
    private final ReportService reportService;
    private final VideoService videoService;

    public YoutubeStatsCommands(VideoService videoService, CommandService commandService, ReportService reportService) {
        this.videoService = videoService;
        this.commandService = commandService;
        this.reportService = reportService;
    }

    @ShellMethod(key = "recent", value = "List recent videos by max count")
    public void recent(@ShellOption(defaultValue = "5") Integer max) {
        List<Video> videos = videoService.findRecent(max);
        TableBuilder tableBuilder = commandService.listToArrayTableModel(videos);
        System.out.println(tableBuilder.build().render(120));
    }

    @ShellMethod(key = "filter-by-year", value = "List videos by year")
    public void byYear(@ShellOption(defaultValue = "2024") Integer year) {
        List<Video> videos = videoService.findAllByYear(year);
        TableBuilder tableBuilder = commandService.listToArrayTableModel(videos);
        System.out.println(tableBuilder.build().render(120));
    }

    @ShellMethod(key = "report", value = "Run a report based on all of the videos for the current year")
    public void report() {
        List<Video> videos = videoService.findAllByYear(LocalDateTime.now().getYear());
        List<TeamTabRow> rows = reportService.videosToTeamTabRows(videos);
        rows.forEach(TeamTabRow::print);
    }
}
