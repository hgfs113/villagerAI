package dev.drdpov.npc;

import java.util.List;
import java.util.UUID;

public record NpcTypeInfo(UUID id, String type, List<String> supportedTasks) {
}
