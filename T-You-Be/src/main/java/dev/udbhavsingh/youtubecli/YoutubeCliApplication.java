package dev.udbhavsingh.youtubecli;

import dev.udbhavsingh.youtubecli.config.YouTubeConfigProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(YouTubeConfigProps.class)
public class YoutubeCliApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubeCliApplication.class, args);
	}

}
