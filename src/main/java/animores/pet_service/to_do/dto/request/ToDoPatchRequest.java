package animores.pet_service.to_do.dto.request;

import animores.pet_service.pet.type.Tag;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ToDoPatchRequest(
        Long profileId,
        @NotNull
        List<Long> petIds,
        Tag tag,
        String content,
        LocalDate date,
        LocalTime time,
        Boolean isAllDay,
        String color,
        Boolean isUsingAlarm
) {
}
