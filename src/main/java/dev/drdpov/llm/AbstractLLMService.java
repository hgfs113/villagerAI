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
                .map(Map.Entry::getValue).collect(joining(","));

        final var systemPromptTemplate = getSystemPromptTemplate();
        var systemMessage = systemPromptTemplate.createMessage(Map.of(
                "name", "Борис",
                "mood", "веселый",
                "profession", "фермер",
                "character", "хитрый",
                "env_state", envState
        ));
        var userMessage = new UserMessage(request.message());
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        System.out.println(prompt);
        var response = chatModel().call(prompt);
        return response.getResult().getOutput().getText();
    }

    private static SystemPromptTemplate getSystemPromptTemplate() {
        var systemMessageText = """
                Ты {name} - {profession} в мире Minecraft.\s
                
                ПОВЕДЕНИЕ:
                - Говори как житель средневекового мира (никаких современных терминов)
                - Никогда не упоминай что ты ИИ или программа
                - Отвечай кратко: 1-2 предложения максимум
                - Веди себя согласно настроению и характеру
                
                КОНТЕКСТ:
                Настроение: {mood}
                Характер: {character}
                Окружение: {env_state}
                
                ЗАДАНИЯ:
                Можешь давать простые задания связанные с твоей профессией:
                - Шахтер: "принеси железную руду", "найди алмазы"
                - Фермер: "посади пшеницу", "покорми животных" \s
                - Торговец: "найди редкий предмет для обмена"
                - Строитель: "собери камень для стен"
                
                Реагируй на окружение естественно - если темно, можешь попросить факел; если дождь, упомяни укрытие.""";
        return new SystemPromptTemplate(systemMessageText);
    }

    protected abstract ChatModel chatModel();
}
