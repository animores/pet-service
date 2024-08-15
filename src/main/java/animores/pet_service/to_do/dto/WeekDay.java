package animores.pet_service.to_do.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum WeekDay {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String name;


    private static final Map<String, WeekDay> namesMap =
            Stream.of(WeekDay.values())
                    .collect(Collectors.toMap(WeekDay::getName, Function.identity()));

    @JsonCreator
    public static WeekDay fromString(String value) {
        return namesMap.get(value.toUpperCase());
    }
}
