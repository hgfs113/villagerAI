package dev.drdpov.llm;

import dev.drdpov.conversation.ConversationRequest;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractLLMService implements LLMService {

    private ChatClient chatClient;
    private String usedModel;

    @Override
    public String generateResponse(ConversationRequest request) {
        var envState = String.join(",", request.envInfo().values());

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
        chatModel().getDefaultOptions().getModel();
        log.debug("Calling [{}] for conversation with id=[{}]", usedModel, request.conversationId());
        return chatClient.prompt(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
                .call()
                .content();
    }

    @PostConstruct
    public void init() {
        var chatMemory = MessageWindowChatMemory.builder().build();
        var chatModel = chatModel();
        chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        usedModel = chatModel.getDefaultOptions().getModel();
    }

    private static SystemPromptTemplate getSystemPromptTemplate() {
        var systemMessageText = """
                Ты {name} - {profession} в мире Minecraft.\s
                
                ПОВЕДЕНИЕ:
                - Говори как житель средневекового мира (никаких современных терминов)
                - Отвечай всегда только на русском языке
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
