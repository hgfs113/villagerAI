package dev.drdpov.npc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/npc")
public class NpcController {

    private final NpcManager npcManager;

    public NpcController(NpcManager npcManager) {
        this.npcManager = npcManager;
    }

    @PostMapping("create-type")
    public ResponseEntity<NpcTypeInfo> createNpcType(@RequestBody CreateNpcTypeRequest request) {
        if (request == null || request.type().isEmpty() && request.supportedTasks().isEmpty()) {
            return ResponseEntity.accepted().build();
        }
        var info = npcManager.createNpcType(request);
        return ResponseEntity.ok(info);
    }
}
