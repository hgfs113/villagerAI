package dev.drdpov.llm;

import dev.drdpov.llm.local.ollama.OllamaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class LlmConfig {

    @Bean
    public OllamaClient ollamaClient() {
        var client = WebClient.builder().baseUrl("http://localhost:11434").build();
        var factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(client)).build();
        return factory.createClient(OllamaClient.class);
    }
}
