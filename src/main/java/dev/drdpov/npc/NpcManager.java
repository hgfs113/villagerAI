package dev.drdpov.npc;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class NpcManager {

    private static final String EMPTY_STRING = "";
    private final Map<UUID, List<String>> TASKS_BY_ID = new HashMap<>();
    private final Map<String, UUID> IDS_BY_TYPE = new HashMap<>();

    public NpcTypeInfo createNpcType(CreateNpcTypeRequest npcTypeInfo) {
        var type = npcTypeInfo.type().filter(a -> !a.isBlank()).orElse(EMPTY_STRING);
        var id = Optional.ofNullable(IDS_BY_TYPE.get(type)).orElse(UUID.randomUUID());
        IDS_BY_TYPE.put(type, id);
        TASKS_BY_ID.put(id, npcTypeInfo.supportedTasks());
        return new NpcTypeInfo(id, type, npcTypeInfo.supportedTasks());
    }
}
