package animores.pet_service.to_do.dao;


import animores.pet_service.pet.type.Tag;
import animores.pet_service.to_do.entity.ToDo;


public record ToDoDao(
Long id,
boolean isAllDay,
String content,
Tag tag,
String color,
boolean isUsingAlarm
) {

    public static ToDoDao fromToDo(ToDo toDo) {
        return new ToDoDao(
                toDo.getId(),
                toDo.isAllDay(),
                toDo.getContent(),
                toDo.getTag(),
                toDo.getColor(),
                toDo.isUsingAlarm()
        );
    }
}
