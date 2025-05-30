package dev.drdpov.llm;

import dev.drdpov.conversation.ConversationRequest;

public interface LLMService {
    String generateResponse(ConversationRequest conversationRequest);
}
