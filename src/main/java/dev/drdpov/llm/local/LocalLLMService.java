package dev.drdpov.llm.local;

import dev.drdpov.llm.AbstractLLMService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

@Service
public class LocalLLMService extends AbstractLLMService {

    private final ChatModel chatModel;
    public LocalLLMService() {
        super();
        var ollamaApi = OllamaApi.builder().build();

        chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(
                        OllamaOptions.builder()
                                .model(OllamaModel.MISTRAL)
                                .temperature(0.9)
                                .keepAlive("3m")
                                .build())
                .build();
    }

    @Override
    protected ChatModel chatModel() {
        return chatModel;
    }
}
