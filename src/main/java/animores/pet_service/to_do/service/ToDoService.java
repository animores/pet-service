package animores.pet_service.to_do.service;

import animores.pet_service.account.entity.Account;
import animores.pet_service.pet.entity.Pet;
import animores.pet_service.to_do.dto.request.ToDoCreateRequest;
import animores.pet_service.to_do.dto.request.ToDoPatchRequest;
import animores.pet_service.to_do.dto.response.ToDoDetailResponse;
import animores.pet_service.to_do.dto.response.ToDoPageResponse;

import java.util.List;

public interface ToDoService {
    void createToDo(Account account, ToDoCreateRequest request);

    ToDoPageResponse getTodayToDo(Boolean done, List<Pet> pets, Integer page, Integer size);

    ToDoPageResponse getAllToDo(Boolean done, List<Pet> pets, Integer page, Integer size);

    ToDoDetailResponse getToDoById(Long id, Long accountId);

    ToDoDetailResponse updateToDoById(Long id, ToDoPatchRequest request, Long accountId);

    void deleteToDoById(Long id, Long profileId);

    void checkToDo(Long toDoId, Long accountId);
}
