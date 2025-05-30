package dev.drdpov.conversation;

import java.util.Map;

public record ConversationRequest(String message, Map<String, String> envInfo) {
}
