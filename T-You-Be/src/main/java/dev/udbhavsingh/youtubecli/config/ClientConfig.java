package dev.udbhavsingh.youtubecli.config;

import dev.udbhavsingh.youtubecli.client.YoutubeDataClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class ClientConfig {
    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory() {
        WebClient client = WebClient.builder()
                .baseUrl("https://youtube.googleapis.com/youtube/v3")
                .build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
    }

    @Bean
    public YoutubeDataClient youTubeDataClient(HttpServiceProxyFactory factory) {
        return factory.createClient(YoutubeDataClient.class);
    }
}
