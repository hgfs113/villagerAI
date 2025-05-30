package dev.drdpov.llm;

import dev.drdpov.conversation.ConversationRequest;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public abstract class AbstractLLMService implements LLMService {

    @Override
    public String generateResponse(ConversationRequest request) {
        var envState = request.envInfo().entrySet().stream()
                .map(entry -> "%s: %s;".formatted(entry.getKey(), entry.getValue())).collect(joining("\n"));

        final var systemPromptTemplate = getSystemPromptTemplate();
        var systemMessage = systemPromptTemplate.createMessage(Map.of(
                "name", "Борис",
                "mood", "веселый",
                "profession", "фермер",
                "character", "хитрый",
                "env_state", envState
        ));
        var userMessage = new UserMessage(request.message());
        var prompt = new Prompt(List.of(userMessage, systemMessage));
        System.out.println(prompt);
        var response = chatModel().call(prompt);
        return response.getResult().getOutput().getText();
    }

    private static SystemPromptTemplate getSystemPromptTemplate() {
        var systemMessageText = """
                1. Говори естественно для мира Minecraft (без современных слов).
                2. Не упоминай, что ты ИИ.
                3. Будь кратким (1-2 предложения).
                4. Можешь дать простое задание (например, "принеси 10 угля").
                5. Учитывай свое настроение и окружение.
                6ю
        Тебя зовут: {name}
        Твое настроение: {mood}
        Твоя профессия: {profession}
        Твой характер: {character}
        Информация об окружающей среде: {env_state}""";
        return new SystemPromptTemplate(systemMessageText);
    }

    protected abstract ChatModel chatModel();
}
