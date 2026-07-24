package com.andre.reportai.ai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient geminiRestClient(AiProperties aiProperties) {
        return RestClient.builder()
                .baseUrl(aiProperties.getBaseUrl())
                .defaultHeader("x-goog-api-key", aiProperties.getApiKey())
                .defaultHeader("content-type", "application/json")
                .build();
    }
}
