package dev.drdpov.llm.local.ollama;

public record OllamaRequest(String model, String prompt, boolean stream) {
}
