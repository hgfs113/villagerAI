package dev.drdpov.llm;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;

public abstract class AbstractLLMService implements LLMService {

    @Override
    public String generateResponse(String userPrompt) {

        var prompt = new Prompt("""
        You are a villager in a Minecraft world and a player wants to talk to you.
        Respond naturally.
        Do not say that you are an AI.
        Stay short (no more than 2 sentences).
        You can give a simple task (ex.: bring 10 stones)
        Take into account your mood, profession, personality and environment.
        Your name: Boris
        Your mood: irritated
        Your profession: farmer
        Your personality: rough but fair
        You must respond in russian
        Here is what user tells you: %s
        """.formatted(userPrompt));
        var response = chatModel().call(prompt);
        return response.getResult().getOutput().getText();
    }

    protected abstract ChatModel chatModel();
}
