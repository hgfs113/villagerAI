package dev.drdpov.conversation;

import dev.drdpov.llm.LLMService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ConversationController {

    private final LLMService llmService;

    public ConversationController(LLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("generate")
    public String generate(@RequestBody ConversationRequest request) {
        return llmService.generateResponse(request.prompt());
    }
}
