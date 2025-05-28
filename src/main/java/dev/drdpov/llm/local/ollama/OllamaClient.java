package dev.drdpov.llm.local.ollama;

import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface OllamaClient {

    @PostExchange("/api/generate")
    OllamaResponse generate(OllamaRequest request);
}
