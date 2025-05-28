package dev.drdpov.npc;

import java.util.List;
import java.util.Optional;

public record CreateNpcTypeRequest(Optional<String> type, List<String> supportedTasks) {
}
