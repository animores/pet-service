package animores.pet_service.to_do.dao;


import animores.pet_service.profile.dao.ProfileVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ToDoInstanceDao(
        Long id,
        ToDoDao toDo,
        LocalDate date,
        LocalTime time,
        ProfileVo completeProfile,
        LocalDateTime completeTime
) {
}
