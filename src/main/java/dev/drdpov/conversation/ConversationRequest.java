package dev.drdpov.conversation;

import java.util.Map;

public record ConversationRequest(String conversationId, String message, Map<String, String> envInfo) {
}
